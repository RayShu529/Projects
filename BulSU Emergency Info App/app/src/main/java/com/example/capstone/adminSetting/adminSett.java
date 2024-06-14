package com.example.capstone.adminSetting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.ImageResizeAndConvertTask;
import com.example.capstone.IncidentReport.AdminReports;
import com.example.capstone.R;
import com.example.capstone.admin.AdminChat;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.adminBuilding.edit.UriToBitmapTask;
import com.example.capstone.adminBuilding.read.AdminBldg;
import com.example.capstone.login.MainActivity;
import com.example.capstone.login.forgotPasswordAsync;
import com.example.capstone.manageFirstAids.manageFaid;
import com.example.capstone.user.UserPage;
import com.example.capstone.user.settings.settings;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class adminSett extends AppCompatActivity {
    LinearLayout ID, PASS,PIC,LCODE;
    ImageView adminPIC,back;
TextInputLayout q,w,e,r,t,y,u;
EditText adminID,passID,passID2,adminPASS,passPASS,passPASS2,passPIC,passPIC2;
Button showID,updateID,showPASS,updatePASS,showPIC,updatePIC,changeCode,showCode;
RelativeLayout rel;
String admin,newCode;
Bitmap b;
TextView code;
    final int MAX_FILE_SIZE = 490;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sett);
        set();
        listeners();
        ActionBar actionBar = getSupportActionBar();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        admin = sharedPreferences.getString("name", "");
        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        new updateAsync(adminSett.this).execute("READ","1","1","");
        new forgotPasswordAsync(adminSett.this, new forgotPasswordAsync.resultListener() {
            @Override
            public void onResult(String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String response = jsonObject.getString("response");
                    code.setText(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("read",admin,"");
    }
    public void setID(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", adminID.getText().toString());
        editor.apply();
        admin = sharedPreferences.getString("name", "");
        adminID.setText("");
    }
    private void set() {
        changeCode = findViewById(R.id.changeCode);
        LCODE = findViewById(R.id.LCODE);
        showCode = findViewById(R.id.showC);
        code = findViewById(R.id.code);
        rel = findViewById(R.id.rel);
        rel.setVisibility(View.VISIBLE);
        back = findViewById(R.id.back);
        ID = findViewById(R.id.ID);
        adminID = findViewById(R.id.adminID);
        passID = findViewById(R.id.passID);
        passID2 = findViewById(R.id.passID2);
        updateID = findViewById(R.id.updateID);
        showID = findViewById(R.id.showID);
        PASS = findViewById(R.id.PASS);
        adminPASS = findViewById(R.id.adminPASS);
        passPASS = findViewById(R.id.passPASS);
        passPASS2 = findViewById(R.id.passPASS2);
        updatePASS = findViewById(R.id.updatePASS);
        showPASS = findViewById(R.id.showPASS);
        PIC = findViewById(R.id.PIC);
        adminPIC = findViewById(R.id.adminPIC);
        passPIC = findViewById(R.id.passPIC);
        passPIC2 = findViewById(R.id.passPIC2);
        updatePIC = findViewById(R.id.updatePIC);
        showPIC= findViewById(R.id.showPIC);
        q = findViewById(R.id.textInputLayout1);w = findViewById(R.id.textInputLayout2);e = findViewById(R.id.textInputLayout3);r = findViewById(R.id.textInputLayout4);
        t = findViewById(R.id.textInputLayout5);y = findViewById(R.id.textInputLayout6);u = findViewById(R.id.textInputLayout7);
        q.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);w.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);e.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        y.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);t.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);r.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        u.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        showPIC.setEnabled(false);
        showID.setEnabled(false);
        showPASS.setEnabled(false);
        showCode.setEnabled(false);
    }
    private void listeners() {
        changeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel.setVisibility(View.VISIBLE);
                newCode="";
                for (int i = 0;i<6;i++){
                    Random random = new Random();
                    // Generate a random integer between 0 (inclusive) and 9 (inclusive)
                    int randomNumber = random.nextInt(10);
                    newCode +=String.valueOf(randomNumber);
                }
                new forgotPasswordAsync(adminSett.this, new forgotPasswordAsync.resultListener() {
                    @Override
                    public void onResult(String s) {
                        rel.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String response = jsonObject.getString("response");
                            if(response.equals("success")){
                                code.setText(newCode);
                                Toast.makeText(adminSett.this, "Reset Code updated", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(adminSett.this, "Server Error.Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute("new",admin,newCode);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        showCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LCODE.getVisibility()==v.VISIBLE){
                    LCODE.setVisibility(v.GONE);

                }
                else{
                    LCODE.setVisibility(v.VISIBLE);
                    ID.setVisibility(View.GONE);
                    PASS.setVisibility(v.GONE);
                    PIC.setVisibility(v.GONE);
                }
            }
        });
        showID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminID.setText("");
                passID.setText("");
                passID2.setText("");
                if(ID.getVisibility()==v.VISIBLE){
                    ID.setVisibility(v.GONE);

                }
                else{
                    ID.setVisibility(v.VISIBLE);
                    PASS.setVisibility(v.GONE);
                    PIC.setVisibility(v.GONE);
                    LCODE.setVisibility(View.GONE);
                }
            }
        });
        showPASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passPASS.setText("");
                passPASS2.setText("");
                adminPASS.setText("");
                if(PASS.getVisibility()==v.VISIBLE){
                    PASS.setVisibility(v.GONE);

                }
                else{
                    PASS.setVisibility(v.VISIBLE);
                    ID.setVisibility(v.GONE);
                    PIC.setVisibility(v.GONE);
                    LCODE.setVisibility(View.GONE);
                }
            }
        });
        showPIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passPIC.setText("");
                passPIC2.setText("");
                if(PIC.getVisibility()==v.VISIBLE){
                    PIC.setVisibility(v.GONE);

                }
                else{
                    PIC.setVisibility(v.VISIBLE);
                    ID.setVisibility(v.GONE);
                    PASS.setVisibility(v.GONE);
                    LCODE.setVisibility(View.GONE);
                }
            }
        });
        adminPIC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                adminPIC.setImageBitmap(null);adminPIC.setImageDrawable(null);
                Drawable drawable = ContextCompat.getDrawable(adminSett.this, R.drawable.addnew);
                adminPIC.setBackground(drawable);
                return true;
            }
        });
        adminPIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        updatePIC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passPIC.setText(passPIC.getText().toString().trim());
                passPIC2.setText(passPIC2.getText().toString().trim());
                Bitmap bitmap = null;
                if(adminPIC.getDrawable()!=null){
                    Drawable drawable = adminPIC.getDrawable();
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    bitmap = bitmapDrawable.getBitmap();
                }
                if (adminPIC.getDrawable()==null){
                    Toast.makeText(adminSett.this, "Select an Image!", Toast.LENGTH_SHORT).show();
                }
                else if(passPIC.getText().toString().equals("")){
                    passPIC.setError("Required");
                }
                else if(!passPIC.getText().toString().equals(passPIC2.getText().toString())){
                    passPIC2.setError("Incorrect Password. Password and Confirm Password must be the same");
                }
                else if(b==bitmap){
                    Toast.makeText(adminSett.this, "Select a new Image", Toast.LENGTH_SHORT).show();
                }
                else{

                    updatePIC.setEnabled(false);passPIC.setEnabled(false);
                    adminPIC.setEnabled(false);passPIC2.setEnabled(false);
                    rel.setVisibility(View.VISIBLE);

                    new BitToArr(adminSett.this,adminSett.this).execute(bitmap);
                }
            }
        });
        updatePASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminPASS.setText(adminPASS.getText().toString().trim());
                passPASS.setText(passPASS.getText().toString().trim());
                passPASS2.setText(passPASS2.getText().toString().trim());
                if (adminPASS.getText().toString().equals("")){
                    adminPASS.setError("Required");
                    }
                else if (adminPASS.getText().toString().length()<6){
                    adminPASS.setError("Password must be at least 6 characters");
                }
                else if(!isInputValid(adminPASS.getText().toString())){
                    adminPASS.setError("Password must have at least 1 UPPERCASE, 1 lowercase, 1 special character and 1 number");
                }
                else if(passPASS.getText().toString().equals("")){
                    passPASS.setError("Required");
                }
                else if(!passPASS.getText().toString().equals(passPASS2.getText().toString())){
                    passPASS2.setError("Incorrect Password. Password and Confirm Password must be the same");
                }
                else{
                    rel.setVisibility(View.VISIBLE);
                    updatePASS.setEnabled(false);
                    adminPASS.setEnabled(false);
                    passPASS.setEnabled(false);
                    passPASS2.setEnabled(false);
                    new updateAsync(adminSett.this).execute("PASS",passPASS.getText().toString(),adminPASS.getText().toString(),admin);
                }
            }
        });
        updateID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminID.setText(adminID.getText().toString().trim());
                passID.setText(passID.getText().toString().trim());
                passID2.setText(passID2.getText().toString().trim());
                if (adminID.getText().toString().equals("")){
                     adminID.setError("Required");
                    }
                else if(adminID.getText().toString().length()!=10){
                    adminID.setError("ID must be exactly 10 characters");
                }
                else if(adminID.getText().toString().equals(admin)){
                    adminID.setError("New ID must be different from the previous one.");
                }
                else if (passID.getText().toString().equals("")){
                    passID.setError("Required");
                }
                else if(!passID.getText().toString().equals(passID2.getText().toString())){
                    passID2.setError("Incorrect Password. Password and Confirm Password must be the same");
                }

                else{
                    rel.setVisibility(View.VISIBLE);
                    updateID.setEnabled(false);
                    adminID.setEnabled(false);
                    passID.setEnabled(false);
                    passID2.setEnabled(false);
                    new updateAsync(adminSett.this).execute("ID",passID.getText().toString(),adminID.getText().toString(),admin);
                }
            }
        });

    }
    public void updateEvac(byte[] bytes){
    new updateAsync(adminSett.this).execute("PIC",passPIC.getText().toString(), Base64.encodeToString(bytes, Base64.DEFAULT),admin);
}
    public void read(String a){
        byte[] bytes = Base64.decode(a, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        adminPIC.setImageBitmap(bmp);
        adminPIC.setBackground(null);
        b = bmp;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                int file_size = inputStream.available()/1024;
                if (file_size <= MAX_FILE_SIZE) {
                    inputStream.close();
                    UriToBitmapTask task = new UriToBitmapTask(this, new UriToBitmapTask.OnBitmapLoadedListener() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap) {
                            adminPIC.setImageBitmap(bitmap);
                            adminPIC.setBackground(null);
                        }
                    });
                    task.execute(data.getData());
                } else {
                    inputStream.close();
                    new ImageResizeAndConvertTask(this, new ImageResizeAndConvertTask.OnImageResizedAndConvertedListener() {
                        @Override
                        public void onImageResizedAndConverted(Uri resizedAndConvertedUri) {
                            check(resizedAndConvertedUri);
                        }
                    }).execute(data.getData());

                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onBackPressed() {
        finish();
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
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = new Intent(adminSett.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(adminSett.this, AdminChat.class);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(adminSett.this,AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(adminSett.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(adminSett.this, MainActivity.class);
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
private void check(Uri uri){
        try{
            InputStream inputStream = getContentResolver().openInputStream(uri);
            int file_size = inputStream.available()/1024;
            inputStream.close();
            if (file_size <= MAX_FILE_SIZE) {
                inputStream.close();
                UriToBitmapTask task = new UriToBitmapTask(this, new UriToBitmapTask.OnBitmapLoadedListener() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap) {
                        adminPIC.setImageBitmap(bitmap);
                        adminPIC.setBackground(null);
                    }
                });
                task.execute(uri);
            }
            else{
                Toast.makeText(this, "Image size is too large. Please choose a smaller image.", Toast.LENGTH_SHORT).show();
                adminPIC.setImageBitmap(null);adminPIC.setImageDrawable(null);
                Drawable drawable = ContextCompat.getDrawable(adminSett.this, R.drawable.addnew);
                adminPIC.setBackground(drawable);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

}
}