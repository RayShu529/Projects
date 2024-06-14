package com.example.capstone.user.evacPlan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.capstone.BulsuPlan.BulsuEvacPlan;
import com.example.capstone.IncidentReport.UserReport;
import com.example.capstone.Messaging.chat;
import com.example.capstone.R;
import com.example.capstone.login.MainActivity;
import com.example.capstone.user.UserPage;

public class showEvacPlan extends AppCompatActivity {
ImageView back,img;
TextView floor,bldgName;
Button f1,f2,f3,f4;
Bitmap[] bitmaps = new Bitmap[4];
Bitmap bitmap;
String name;
int ctr=0;
RelativeLayout rel;
    float degreesToRotate = 90.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_evac_plan);
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
        Intent i = getIntent();
        name = i.getStringExtra("name");
        bldgName.setText(name);
        new showEvacPlanAsync(showEvacPlan.this).execute(name,"1");

    }
    public void showPic(String a, int b,int c){
        if(ctr == 0){
            ctr=1;
            setVis(c);
        }
        byte[] bytes = Base64.decode(a, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if(b==1){
            bitmaps[0] = bmp;
            img.setImageBitmap(bitmaps[0]);
            floor.setText("1st Floor");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(showEvacPlan.this);
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
        else if(b==2){
            bitmaps[1] = bmp;

        }
        else if(b==3){
            bitmaps[2] = bmp;
        }
        else if(b==4){
            bitmaps[3] = bmp;
        }
        if (b<c){
            String q = String.valueOf(b+1);
            new showEvacPlanAsync(showEvacPlan.this).execute(name,q);
        }
    }
    private void set() {
        back = findViewById(R.id.back);
        img = findViewById(R.id.img);
        floor = findViewById(R.id.floor);
        bldgName = findViewById(R.id.bldgName);
        f1 = findViewById(R.id.f1);
        f2 = findViewById(R.id.f2);
        f3 = findViewById(R.id.f3);
        f4 = findViewById(R.id.f4);

    }
    private void listener() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(bitmaps[0]);
                floor.setText("1st Floor");

            }
        });
        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(bitmaps[1]);
                floor.setText("2nd Floor");


            }
        });
        f3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(bitmaps[2]);
                floor.setText("3rd Floor");


            }
        });
        f4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img.setImageBitmap(bitmaps[3]);
                floor.setText("4th Floor");

            }
        });
    }
    public  void setVis(int a){
        if(a==1){
            f1.setVisibility(View.VISIBLE);
        }
        else if(a==2){
            f1.setVisibility(View.VISIBLE);f2.setVisibility(View.VISIBLE);
        }
        else if(a==3){
            f1.setVisibility(View.VISIBLE);f2.setVisibility(View.VISIBLE);f3.setVisibility(View.VISIBLE);
        }
        else if(a==4) {
            f1.setVisibility(View.VISIBLE);f2.setVisibility(View.VISIBLE);f3.setVisibility(View.VISIBLE);f4.setVisibility(View.VISIBLE);
        }
        rel.setVisibility(View.GONE);
    }
    public Bitmap rotateBitmap(Bitmap source, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);

        // Retrieve the button item from the menu
        MenuItem chat = menu.findItem(R.id.nav_chat);
        MenuItem home = menu.findItem(R.id.nav_home);
        MenuItem logout = menu.findItem(R.id.nav_logout);
        MenuItem report = menu.findItem(R.id.report);
        report.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(showEvacPlan.this, UserReport.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(showEvacPlan.this, com.example.capstone.Messaging.chat.class);
                intent.putExtra("name","Admin");
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(showEvacPlan.this,UserPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(showEvacPlan.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(showEvacPlan.this, MainActivity.class);
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
    @Override
    public void onBackPressed() {
        finish();
    }
}