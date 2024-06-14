package com.example.capstone.BulsuPlan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.R;
import com.example.capstone.admin.AdminChat;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.adminNotif.Maps;
import com.example.capstone.adminNotif.sendNotif;
import com.example.capstone.adminSetting.adminSett;
import com.example.capstone.adminSetting.updateAsync;
import com.example.capstone.user.UserPage;
import com.google.android.gms.maps.model.LatLng;

public class BulsuEvacPlan extends AppCompatActivity {
ImageView bulsuEvacPic,back;
TextView msg;
Bitmap bitmap;
Button showDirection;
RelativeLayout rel;
    float degreesToRotate = 90.0f;
    Double lat,lon;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulsu_evac_plan);
        set();
        listener();
        ActionBar actionBar = getSupportActionBar();
rel = findViewById(R.id.rel);
rel.setVisibility(View.VISIBLE);
        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        new BulsuAsync(BulsuEvacPlan.this).execute("READ","1","1");
        new GetLocationAsync(BulsuEvacPlan.this).execute();
    }
    public void show(String a){
        rel.setVisibility(View.GONE);
        byte[] bytes = Base64.decode(a, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if(bmp!=null){
            bulsuEvacPic.setImageBitmap(bmp);
            bulsuEvacPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(BulsuEvacPlan.this);
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
            if(bmp.getHeight()<bmp.getWidth()){
                bitmap= rotateBitmap(bmp, degreesToRotate);
            }

            else{
                bitmap=bmp;
            }
        }
    }
public void up(){
    Intent intent = getIntent();
    if (intent.hasExtra("msg")) {
        msg.setText(message);
        showDirection.setVisibility(View.VISIBLE);
    }
}
    private void set() {
        bulsuEvacPic =findViewById(R.id.bulsuEvacPic);
        back = findViewById(R.id.back);
        msg = findViewById(R.id.msg);
        showDirection = findViewById(R.id.showDirection);
    }
    private void listener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lat!=null && lon!=null){
                    sd();
                }
                else{
                    showDirection.setEnabled(false);

                    handler.post(runnable);
                }
            }
        });
    }
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sd();
            handler.postDelayed(this, 3000); // Schedule the code to run again after 1 second (1000 milliseconds)
        }
    };
    private void sd() {
        if(lat!=null && lon!=null){
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lon);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name of Google Maps
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                // Start the Google Maps app with the directions
                startActivityForResult(mapIntent,100);
            }
            showDirection.setEnabled(true);

        }
        else{
            Toast.makeText(this, "Loading...Please wait...", Toast.LENGTH_SHORT).show();
        }
    }
    public Bitmap rotateBitmap(Bitmap source, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Remove the callback to stop the code from running when the activity is destroyed
        handler.removeCallbacks(runnable);
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }
}