package com.example.capstone.adminNotif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.example.capstone.R;

public class Maps extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    private GoogleMap googleMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private FusedLocationProviderClient fusedLocationClient;
    ImageView back,current;
    Marker marker = null;
    Button select;
    LatLng initialLocation = new LatLng(14.858332631484501, 120.81440599390464);
    LatLng selected = null;
    private LocationRequest locationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5000); // Update interval in milliseconds (e.g., every 5 seconds)
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        select = findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected == null) {
                    Toast.makeText(Maps.this, "Select a Location", Toast.LENGTH_SHORT).show();
                } else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("picked_point", selected);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });

        current = findViewById(R.id.current);
        current.setEnabled(false);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(Maps.this);
                if (ContextCompat.checkSelfPermission(Maps.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Maps.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                    // Location permissions are already granted, request the location once
                    requestOneTimeLocation();
                }
            }
        });

    }

    private void requestOneTimeLocation() {


        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);

        // Check if the location settings are satisfied
        settingsClient.checkLocationSettings(builder.build())
                .addOnSuccessListener(this, locationSettingsResponse -> {
                    // All location settings are satisfied, request the location once
                    Log.e("Location", "Location settings are satisfied");
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.e("Location", "Permission not granted");
                        return;
                    }
                    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                })
                .addOnFailureListener(this, e -> {
                    if (e instanceof ResolvableApiException) {
                        // Location settings are not satisfied, but can be fixed by showing the user a dialog
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(this, 123);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        // Handle other location-related errors
                        e.printStackTrace();
                    }
                });
    }


    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            for (Location location : locationResult.getLocations()) {
                // Handle the updated location here (e.g., update UI, store the location, etc.)
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng current = new LatLng(latitude, longitude);
                if (marker!=null){
                    marker.remove();
                }
                selected = current;
                MarkerOptions markerOptions = new MarkerOptions().position(current).title("Current Location");
                marker = googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 20));
                fusedLocationClient.removeLocationUpdates(locationCallback);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permissions granted, request the location once
                requestOneTimeLocation();
            } else {
                // Location permissions denied, handle accordingly (e.g., show a message)
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap map) {
        current.setEnabled(true);
        googleMap = map;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 16));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker!=null){
                    marker.remove();
                }
                selected = latLng;
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Selected Location");
                marker = googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}