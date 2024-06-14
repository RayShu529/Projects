package com.example.capstone.register;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.capstone.ImageResizeAndConvertTask;
import com.example.capstone.R;
import com.example.capstone.adminBuilding.edit.UriToBitmapTask;
import com.example.capstone.login.MainActivity;
import com.example.capstone.manageFirstAids.PictureToArray;
import com.example.capstone.manageFirstAids.manageFaid;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class RegPage extends AppCompatActivity {
ImageView back,pic;
EditText id,pass,fname,lname,pass2;
Button reg;
String sID, sPASS,sFNAME,sLNAME;
TextInputLayout textInputLayout,textInputLayout2 ;
    RelativeLayout rel;
    final int MAX_FILE_SIZE = 490;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_page);
        set();
        listener();
        // Set the icon for the action bar
        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();
        }

    }

    private void set() {
        rel = findViewById(R.id.rel);
        textInputLayout = findViewById(R.id.textInputLayout);textInputLayout2 = findViewById(R.id.textInputLayout2);
        textInputLayout.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);textInputLayout2.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        back = findViewById(R.id.back);
        id = findViewById(R.id.id);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        pass = findViewById(R.id.pass);
        pass2 = findViewById(R.id.pass2);
        reg = findViewById(R.id.reg);
        reg.setEnabled(true);
        pic = findViewById(R.id.pic);
    }

    private void listener() {
        pic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pic.setImageBitmap(null);
                pic.setImageDrawable(null);
                Drawable drawable = ContextCompat.getDrawable(RegPage.this, R.drawable.person);
                pic.setBackground(drawable);
                return true;
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass2.setText(pass2.getText().toString().trim());
                pass.setText(pass.getText().toString().trim());
                id.setText(id.getText().toString().trim());
                fname.setText(fname.getText().toString().trim());
                lname.setText(lname.getText().toString().trim());
                sID = id.getText().toString();
                sLNAME = lname.getText().toString();
                sFNAME = fname.getText().toString();
                sPASS = pass.getText().toString();
                if(pic.getDrawable()==null){
                    Toast.makeText(RegPage.this, "Select an Image", Toast.LENGTH_SHORT).show();
                }
                else if(sID.equals("")){
                    id.setError("Required");
                }
                else if(sID.length()!=10){
                    id.setError("ID must be exactly 10 characters");
                    }
                else if(sFNAME.equals("")){
                    fname.setError("Required");
                }
                else if(sLNAME.equals("")){
                    lname.setError("Required");
                }
                else if(sPASS.equals("")){
                    pass.setError("Required");
                }
                else if(sPASS.length()<6){
                    pass.setError("Password must be at least 6 characters");
                }
                else if(!isInputValid(sPASS)){
                    pass.setError("Password must have at least 1 UPPERCASE, 1 lowercase, 1 special character and 1 number");
                }
                else if(!sPASS.equals(pass2.getText().toString().trim())){
                    pass2.setError("Incorrect Password. Password and Confirm Password must be the same");
                }
                else{
                    reg.setEnabled(false);id.setEnabled(false);
                    pass.setEnabled(false);pass2.setEnabled(false);
                    fname.setEnabled(false);lname.setEnabled(false);
                    rel.setVisibility(View.VISIBLE);
                    regsync register = new regsync(RegPage.this);
                    register.execute(sID,sFNAME,sLNAME,"check", "");
                }

            }
        });

    }
    public void checked(){
        Drawable drawable = pic.getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        new regPicToArray(RegPage.this,RegPage.this).execute(bitmap);
    }
    public void send(byte[] bytes){
        regsync register = new regsync(RegPage.this);
        register.execute(sID,sFNAME,sLNAME,sPASS, Base64.encodeToString(bytes, Base64.DEFAULT));
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
                            pic.setImageBitmap(bitmap);
                            pic.setBackground(null);
                        }
                    });
                    task.execute(data.getData());
                } else {
                    inputStream.close();
                    new ImageResizeAndConvertTask(this, new ImageResizeAndConvertTask.OnImageResizedAndConvertedListener() {
                        @Override
                        public void onImageResizedAndConverted(Uri resizedAndConvertedUri) {
                            try{
                                InputStream inputStream = getContentResolver().openInputStream(resizedAndConvertedUri);
                                int file_size = inputStream.available()/1024;
                                inputStream.close();
                                if (file_size <= MAX_FILE_SIZE) {
                                    inputStream.close();
                                    UriToBitmapTask task = new UriToBitmapTask(RegPage.this, new UriToBitmapTask.OnBitmapLoadedListener() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap) {
                                            pic.setImageBitmap(bitmap);
                                            pic.setBackground(null);
                                        }
                                    });
                                    task.execute(resizedAndConvertedUri);
                                }
                                else{
                                    Toast.makeText(RegPage.this, "Image size is too large. Please choose a smaller image.", Toast.LENGTH_SHORT).show();
                                    pic.setImageBitmap(null);pic.setImageDrawable(null);
                                    Drawable drawable = ContextCompat.getDrawable(RegPage.this, R.drawable.person);
                                    pic.setBackground(drawable);
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).execute(data.getData());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}