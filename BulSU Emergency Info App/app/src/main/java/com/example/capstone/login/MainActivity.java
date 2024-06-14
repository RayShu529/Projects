package com.example.capstone.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.BulsuPlan.BulsuEvacPlan;
import com.example.capstone.MyForegroundService;
import com.example.capstone.R;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.register.RegPage;
import com.example.capstone.user.UserPage;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {
Button log;
EditText id, pass;
TextView reg, forgot;
String sID, sPASS;
TextInputLayout textInputLayout;
RelativeLayout rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        set();
        listeners();
        UserPage.id="";

        FirebaseMessaging.getInstance().subscribeToTopic("all_users");
        ActionBar actionBar = getSupportActionBar(); // For AndroidX, use getSupportActionBar()

        // Set the icon for the action bar
        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        String name = sharedPreferences.getString("name", "");
        if(id.equals("admin")){

            Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, AdminPage.class);
            startActivity(i);

        }
        else if(!id.equals("")){
            Toast.makeText(this, "Welcome "+name, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, UserPage.class);
            startActivity(i);
        }
        else if(id.equals("")){
            Intent serviceIntent = new Intent(this, MyForegroundService.class);
            serviceIntent.putExtra("stop", "stopMyForegroundService");
            stopService(serviceIntent);
        }
        Intent intent=new Intent(this, BulsuEvacPlan.class);
        Intent fromIntent = getIntent();
        if (fromIntent.getExtras()!=null){
            if(!fromIntent.hasExtra("profile")){
                String msg = "Go to this Location";
                intent.putExtra("msg", msg);
                startActivity(intent);
            }
        }
    }
public void userSet(String id , String name){
    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("id", id);
    editor.putString("name", name);
    editor.apply();
    Intent serviceIntent = new Intent(MainActivity.this, MyForegroundService.class);
    serviceIntent.putExtra("id", id);
    ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
}
    @Override
    protected void onResume() {
        super.onResume();
        id.setText("");
        pass.setText("");
    }

    private void set() {
        rel = findViewById(R.id.rel);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        log = findViewById(R.id.log);
        reg = findViewById(R.id.reg);
        forgot = findViewById(R.id.forgot);
        id = findViewById(R.id.id);
        pass = findViewById(R.id.pass);
        log.setEnabled(true);
    }
    private void listeners() {
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, forgotPassword.class);
                startActivity(i);
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pass.setText(pass.getText().toString().trim());
                id.setText(id.getText().toString().trim());
                sID = id.getText().toString();
                sPASS = pass.getText().toString();
                if(sID.equals("")){
                    id.setError("Required");
                }
                else if(sID.length()!=10){
                    id.setError("ID must be exactly 10 characters");
                    }
                else if(sPASS.equals("")){
                    pass.setError("Required");
                }

                else {
                    rel.setVisibility(View.VISIBLE);
                    log.setEnabled(false);id.setEnabled(false);pass.setEnabled(false);
                    logsync login = new logsync(MainActivity.this);
                    login.execute(sID,sPASS);
                }
            }
        });
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegPage.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onPostResume() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        if(id.equals("")){
            Intent serviceIntent = new Intent(this, MyForegroundService.class);
            serviceIntent.putExtra("stop", "stopMyForegroundService");
            startService(serviceIntent);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("all_users");
        super.onPostResume();
    }

}








