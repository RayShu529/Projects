package com.example.capstone.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.IncidentReport.AdminReports;
import com.example.capstone.R;
import com.example.capstone.adminSetting.adminSett;
import com.example.capstone.login.MainActivity;
import com.example.capstone.manageFirstAids.firstAidAsync;
import com.example.capstone.manageFirstAids.manageFaid;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class ManageAdmin extends AppCompatActivity {
ImageView back;
TextView removetv;
Spinner removespinner;
Button addbutton,removebutton,showAdd,showRemove;
EditText addid,addpass,adminpass,removepass;
TextInputLayout addtextinput1,addtextinput2,removetextinput;
LinearLayout Ladd, Lremove;
RelativeLayout rel;
String admin;
int checker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admin);
        setView();
        setListener();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        admin = sharedPreferences.getString("name", "");
        syncData();
    }

    public void syncData() {
        rel.setVisibility(View.VISIBLE);
        addid.setText("");
        addpass.setText("");
        adminpass.setText("");
        removepass.setText("");
        new ManageAdminAsync(ManageAdmin.this).execute("","","","","read");
    }

    private void setView() {
        showAdd = findViewById(R.id.showAdd);
        showRemove = findViewById(R.id.showRemove);
        removetv = findViewById(R.id.removetv);
        removespinner = findViewById(R.id.removespinner);
        removebutton = findViewById(R.id.removebutton);
        removepass = findViewById(R.id.removepass);
        Lremove = findViewById(R.id.Lremove);
        rel = findViewById(R.id.rel);
        back = findViewById(R.id.back);
        addbutton = findViewById(R.id.addbutton);
        addid = findViewById(R.id.addid);
        addpass = findViewById(R.id.addpass);
        adminpass = findViewById(R.id.adminpass);
        Ladd = findViewById(R.id.Ladd);
        addtextinput1 = findViewById(R.id.addtextinput1);addtextinput1.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        addtextinput2 = findViewById(R.id.addtextinput2);addtextinput2.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        removetextinput = findViewById(R.id.removetextinput);removetextinput.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
    }
    private void setListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ladd.setVisibility(View.VISIBLE);
                Lremove.setVisibility(View.GONE);
            }
        });
        showRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ladd.setVisibility(View.GONE);
                Lremove.setVisibility(View.VISIBLE);
            }
        });
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = addid.getText().toString().trim();
                String pass = addpass.getText().toString().trim();
                String adminp = adminpass.getText().toString().trim();
                addid.setText(id);
                addpass.setText(pass);
                adminpass.setText(adminp);
                if(id.equals("")){
                    addid.setError("Required");
                }
                else if(id.length()!=10){
                    addid.setError("ID must be 10 characters long");
                }
                else if(pass.equals("")){
                    addpass.setError("Required");
                }
                else if(pass.length()<6){
                    addpass.setError("Password must 6 characters long");
                }
                else if(!isInputValid(pass)){
                    addpass.setError("Password must have at least 1 UPPERCASE, 1 lowercase, 1 special character and 1 number");
                }
                else if(adminp.equals("")){
                    adminpass.setError("Required");
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ManageAdmin.this);
                    builder.setTitle("Add Admin")
                            .setMessage("Admin "+id+" will have access to manage the app.\nAre you sure about that?")
                            .setCancelable(true)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    showAdd.setEnabled(false);
                                    showRemove.setEnabled(false);
                                    addbutton.setEnabled(false);
                                    addid.setEnabled(false);
                                    addpass.setEnabled(false);
                                    adminpass.setEnabled(false);
                                    rel.setVisibility(View.VISIBLE);
                                    new ManageAdminAsync(ManageAdmin.this).execute(id,pass,admin,adminp,"add");
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
        });
        removebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker==0){
                    String name = removespinner.getSelectedItem().toString();
                    String pass = removepass.getText().toString().trim();
                    if (pass.equals("")){
                        removepass.setError("Required");
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(ManageAdmin.this);
                        builder.setTitle("Archive Admin")
                                .setMessage("Admin "+name+" will be archived and will no longer have access to manage the app.\nAre you sure about that?")
                                .setCancelable(true)
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        showAdd.setEnabled(false);
                                        showRemove.setEnabled(false);
                                        removebutton.setEnabled(false);
                                        removespinner.setEnabled(false);
                                        removepass.setEnabled(false);
                                        rel.setVisibility(View.VISIBLE);
                                        new ManageAdminAsync(ManageAdmin.this).execute(name,"",admin,pass,"remove");
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
                    Toast.makeText(ManageAdmin.this, "No Admin to remove.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getList(ArrayList<String> list){
        if(list.size()==0){
            removespinner.setVisibility(View.GONE);
            removetv.setVisibility(View.VISIBLE);
            checker=1;
        }
        else{
            checker=0;
            removespinner.setVisibility(View.VISIBLE);
            removetv.setVisibility(View.GONE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
// Set the dropdown layout style (optional)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Set the adapter for the Spinner
            removespinner.setAdapter(adapter);
        }
    }
    private boolean isInputValid(String text) {
        // Define regular expressions for each criteria
        String uppercaseRegex = ".*[A-Z].*";
        String lowercaseRegex = ".*[a-z].*";
        String numberRegex = ".*\\d.*";
        String specialCharacterRegex = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*";

        // Check if the input matches all criteria
        return text.matches(uppercaseRegex) &&
                text.matches(lowercaseRegex) &&
                text.matches(numberRegex) &&
                text.matches(specialCharacterRegex);
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
                Intent intent = new Intent(ManageAdmin.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ManageAdmin.this, AdminChat.class);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(ManageAdmin.this,AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageAdmin.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(ManageAdmin.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
}