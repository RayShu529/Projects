package com.example.capstone.adminNotif;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.IncidentReport.AdminReports;
import com.example.capstone.R;
import com.example.capstone.admin.AdminChat;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.adminBuilding.read.AdminBldg;
import com.example.capstone.adminSetting.adminSett;
import com.example.capstone.login.MainActivity;
import com.example.capstone.user.UserPage;
import com.example.capstone.user.evacPlan.showEvacPlan;
import com.google.android.gms.maps.model.LatLng;

public class sendNotif extends AppCompatActivity {
    ImageView back,img;
RelativeLayout rel;
    Button se,loc;
    EditText ms;
    String Token;
    Bitmap bitmap;
    LatLng latLng = null;
    TextView lat,lon;
    float degreesToRotate = 90.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notif);
        set();
        listeners();
        rel = findViewById(R.id.rel);
        ActionBar actionBar = getSupportActionBar();

        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        new evacPlan(sendNotif.this).execute();
        rel.setVisibility(View.VISIBLE);
    }
    private void set() {
        back = findViewById(R.id.back);
        img = findViewById(R.id.img);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        se = findViewById(R.id.se);
        ms = findViewById(R.id.ms);
        loc = findViewById(R.id.loc);
    }
    private void listeners() {
loc.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent pickPointIntent = new Intent(sendNotif.this, Maps.class);
        startActivityForResult(pickPointIntent, 10);

    }
});
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ms.setText(ms.getText().toString().trim());
                if(ms.getText().toString().equals("")){
                    ms.setError("Input Message");
                }
                else if(latLng==null){
                    Toast.makeText(sendNotif.this, "Select Location", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(sendNotif.this, "Alert Sent", Toast.LENGTH_SHORT).show();
                    new LocationAsync().execute(Double.toString(latLng.latitude),Double.toString(latLng.longitude),ms.getText().toString());
                    new sendAsync().execute(ms.getText().toString());
                    latLng = null;
                    lat.setText("Latitude: ");lon.setText("Longtitude: ");
                    ms.setText("");
                }
            }
        });

    }
    public void read(String response){
        byte[] bytes = Base64.decode(response, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if(bmp!=null){
            img.setImageBitmap(bmp);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bitmap bm=((BitmapDrawable)img.getDrawable()).getBitmap();
                    if(bm.getHeight()<bm.getWidth()){
                        bitmap= rotateBitmap(bm, degreesToRotate);
                    }
                    else{
                        bitmap=bm;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(sendNotif.this);
                    View customLayout = getLayoutInflater().inflate(R.layout.customalert, null);
                    ImageView imageView = customLayout.findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);
                    builder.setView(customLayout);
                    builder.setCancelable(true);
                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }

    }
    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        MenuItem chat = menu.findItem(R.id.nav_chat);
        
        MenuItem home = menu.findItem(R.id.nav_home);
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem report = menu.findItem(R.id.report);
        report.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(sendNotif.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(sendNotif.this, AdminChat.class);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(sendNotif.this,AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(sendNotif.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(sendNotif.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity(i);
                        finish();
                    }
                });
                builder.setCancelable(true);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
        return true;
    }
    public Bitmap rotateBitmap(Bitmap source, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                latLng = (LatLng) data.getParcelableExtra("picked_point");
                lat.setText("Latitude: "+latLng.latitude);
                lon.setText("Longtitude: "+latLng.longitude);
            }
        }
    }

}