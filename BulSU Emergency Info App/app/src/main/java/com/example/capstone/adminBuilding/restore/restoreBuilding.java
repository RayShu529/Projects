package com.example.capstone.adminBuilding.restore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.example.capstone.manageFirstAids.firstAidAsync;
import com.example.capstone.manageFirstAids.manageFaid;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class restoreBuilding extends AppCompatActivity {
ImageView back;
TextInputLayout restoretextinput;
TextView restoretv;
Button restorebutton;
EditText restorepass;
Spinner restorebldg;
RelativeLayout rel;
int checker;
String admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_building);
        back = findViewById(R.id.back);
        restoretextinput = findViewById(R.id.restoretextinput);
        restoretv = findViewById(R.id.restoretv);
        restorebutton = findViewById(R.id.restorebutton);
        restorepass = findViewById(R.id.restorepass);
        restorebldg = findViewById(R.id.restorebldg);
        rel = findViewById(R.id.rel);
        restoretextinput.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        sync();
        listener();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        admin = sharedPreferences.getString("name", "");
    }
    private void listener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        restorebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker==0){
                    String name = restorebldg.getSelectedItem().toString();
                    String pass = restorepass.getText().toString().trim();
                    if (pass.equals("")){
                        restorepass.setError("Required");
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(restoreBuilding.this);
                        builder.setTitle("Restore Building")
                                .setMessage("The Building "+name+" will be restored and will be available for viewing and editing.\nAre you sure about that?")
                                .setCancelable(true)
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        restorebutton.setEnabled(false);restorebldg.setEnabled(false);
                                        restorepass.setEnabled(false);rel.setVisibility(View.VISIBLE);
                                        new restoreAsync(restoreBuilding.this).execute("restore",name,pass,admin);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Code to handle the negative button click
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                }
                else{
                    Toast.makeText(restoreBuilding.this, "No Building to restore.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    public void sync(){
        rel.setVisibility(View.VISIBLE);
        restorepass.setText("");
        new restoreAsync(restoreBuilding.this).execute("read","","","");
    }
    public void getList(ArrayList<String> list){
        if(list.size()==0){
            restoretv.setVisibility(View.VISIBLE);
            restorebldg.setVisibility(View.GONE);
            checker=1;
        }
        else{
            checker=0;
            restoretv.setVisibility(View.GONE);
            restorebldg.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            restorebldg.setAdapter(adapter);
        }
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
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(restoreBuilding.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(restoreBuilding.this, AdminChat.class);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(restoreBuilding.this,AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(restoreBuilding.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
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
}