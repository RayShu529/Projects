package com.example.capstone.IncidentReport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.R;

import com.example.capstone.adminNotif.Maps;

import com.example.capstone.user.UserPage;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UserReport extends AppCompatActivity {
ImageView img,back;
Spinner incident;
EditText situation;
TextView location;
Button send;
Bitmap imageBitmap;
RelativeLayout rel;
    String dateTime,caseNumber,dt,id;
    private Handler handler;
    LatLng latLng = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report);
        findID();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");

        ActionBar actionBar = getSupportActionBar();
        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void findID() {
        img = findViewById(R.id.img);
        back = findViewById(R.id.back);
        incident = findViewById(R.id.incident);
        ArrayList<String> list = new ArrayList<>();
        list.add("Fire");
        list.add("Earthquake");
        list.add("Bomb Threat");
        list.add("Severe Weather");
        list.add("Hurricane");
        list.add("Lockdown");
        list.add("Flood");
        list.add("Active Shooter");
        list.add("Others");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incident.setAdapter(adapter);
        situation = findViewById(R.id.situation);
        location = findViewById(R.id.location);
        send = findViewById(R.id.send);
        rel = findViewById(R.id.rel);
        listeners();

    }

    private void listeners() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPointIntent = new Intent(UserReport.this, Maps.class);
                startActivityForResult(pickPointIntent, 10);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UserReport.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Request CAMERA permission
                    ActivityCompat.requestPermissions(UserReport.this, new String[]{Manifest.permission.CAMERA}, 69);
                } else {
                    // Permission is already granted, open the camera
                    openCamera();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                situation.setText(situation.getText().toString().trim());
                if(img.getDrawable()==null){
                    Toast.makeText(UserReport.this, "Image Needed for report", Toast.LENGTH_SHORT).show();
                }
                else if(location.getText().toString().equals("")){

                    Toast.makeText(UserReport.this, "Location is needed for report", Toast.LENGTH_SHORT).show();
                }
                else if (situation.getText().toString().equals("")){
                    situation.setError("Brief detailed of situation is needed for report");
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserReport.this);
                    builder.setTitle("Send Report")
                            .setMessage("Warning: Transmitting inaccurate reports may lead to warnings or suspensions. Please refrain from submitting false information.")
                            .setCancelable(true)
                            .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    rel.setVisibility(View.VISIBLE);
                                    send.setEnabled(false);
                                    sendImage();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        handler = new Handler();
        updateCurrentTime();
    }
    private void updateCurrentTime() {
        // Create a new Runnable that updates the time and post it to the handler
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Get the current time
                dateTime = getCurrentDateTime();
                // Schedule the next update after 1000 milliseconds (1 second)
                handler.postDelayed(this, 1000);
            }
        });}
    private String getCurrentDateTime() {
        // Get the current date and time in the desired format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    protected void onDestroy() {
        // Remove any remaining callbacks to prevent memory leaks
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
    private void sendImage(){
        dt = dateTime;
        caseNumber =id.substring(6)+dateTime.replace(" ","").replace("-","").replace(":","").substring(2);
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            int bitmapSizeKB = byteArrayOutputStream.size() / 1024;
            byteArrayOutputStream.close();
            if (bitmapSizeKB<=490) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                String pic = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
                new UserReportAsync(UserReport.this).execute(caseNumber,"image",pic,dt,"",id);
            }
            else if(bitmapSizeKB<=2000) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 75, stream);
                String pic = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
                new UserReportAsync(UserReport.this).execute(caseNumber,"image",pic,dt,"",id);
            }
            else if(bitmapSizeKB<=4000){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                String pic = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
                new UserReportAsync(UserReport.this).execute(caseNumber,"image",pic,dt,"",id);
            }
            else{
                Toast.makeText(this, "Image is too big. Please take another image", Toast.LENGTH_SHORT).show();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendDetails(){
        String emergency = incident.getSelectedItem().toString();
        String loc = location.getText().toString();
        String situ = situation.getText().toString();
        new UserReportAsync(UserReport.this).execute(caseNumber,"details",emergency,loc,situ,id);
    }
    private void openCamera(){
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 15);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 15 && resultCode == RESULT_OK) {
            // Get the captured image and display it
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            this.imageBitmap = imageBitmap;
            img.setImageBitmap(imageBitmap);
            img.setBackground(null);
        }
        if (requestCode == 10 && resultCode==RESULT_OK){
            latLng = (LatLng) data.getParcelableExtra("picked_point");
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Latitude", latLng.latitude);
                jsonObject.put("Longitude", latLng.longitude);
                location.setText(jsonObject.toString());
            } catch (JSONException e) {
                location.setText("");
                Toast.makeText(this, "Error. Please select another Location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 69) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission is granted, open the camera
                openCamera();
            } else {
                // Permission denied, handle the situation (e.g., show a message or request permission again)
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}