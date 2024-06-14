package com.example.capstone.user.hotline;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.Manifest;

import com.example.capstone.IncidentReport.UserReport;
import com.example.capstone.R;
import com.example.capstone.login.MainActivity;
import com.example.capstone.user.UserPage;
import com.example.capstone.user.evacPlan.userBldg;

public class call extends AppCompatActivity {
Button pc,al,ff,rc;
ImageView back;
String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_lines);
        set();
        listener();
        ActionBar actionBar = getSupportActionBar();

        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
    private void set() {
        pc = findViewById(R.id.pc);
        al = findViewById(R.id.al);
        ff = findViewById(R.id.ff);
        rc = findViewById(R.id.rc);
        back = findViewById(R.id.back);
    }
    private void listener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = "0447962483";
                initiatecall();
            }
        });
        al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = "0448216750";
                initiatecall();
            }
        });
        ff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = "0447916129";
                initiatecall();
            }
        });
        rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = "0446625922";
                initiatecall();
            }
        });
    }
public void initiatecall(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 23);
        }
        else {
            Uri phoneNumberUri = Uri.parse("tel:" + num);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(phoneNumberUri);
            startActivityForResult(intent, 1);

        }
    } else {
        Uri phoneNumberUri = Uri.parse("tel:" + num);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(phoneNumberUri);
        startActivityForResult(intent, 1);

    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

        }
    }
    @Override
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
                Intent intent = new Intent(call.this, UserReport.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(call.this, com.example.capstone.Messaging.chat.class);
                intent.putExtra("name","Admin");
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(call.this, UserPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(call.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(call.this, MainActivity.class);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 23) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now make a phone call
                Uri phoneNumberUri = Uri.parse("tel:" + num);
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(phoneNumberUri);
                startActivityForResult(intent, 1);
            } else {
                // Permission denied, handle this case gracefully (e.g., show a message to the user)
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}