package com.example.capstone.user.settings;

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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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
import com.example.capstone.IncidentReport.UserReport;
import com.example.capstone.R;
import com.example.capstone.adminBuilding.edit.UriToBitmapTask;
import com.example.capstone.login.MainActivity;
import com.example.capstone.register.RegPage;
import com.example.capstone.register.regPicToArray;
import com.example.capstone.register.regsync;
import com.example.capstone.user.UserPage;
import com.example.capstone.user.evacPlan.userBldg;
import com.example.capstone.login.forgotPasswordAsync;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class settings extends AppCompatActivity {
ImageView back,pic;
EditText op,np,np2,passPic;
Button cp,changePic,showPic,showPass,changeCode,showCode;
RelativeLayout rel;
TextView code;
TextInputLayout textInputLayout1,textInputLayout2,textInputLayout3,textInputLayout4;
LinearLayout LPIC,LPASS,LCODE;
String pass,newCode;
    final int MAX_FILE_SIZE = 490;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        set();
        ActionBar actionBar = getSupportActionBar();
rel = findViewById(R.id.rel);

        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        listener();
        rel.setVisibility(View.VISIBLE);
        new forgotPasswordAsync(settings.this, new forgotPasswordAsync.resultListener() {
            @Override
            public void onResult(String s) {
                rel.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String response = jsonObject.getString("response");
                    code.setText(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("read",UserPage.id,"");
    }
    private void set() {
        showCode = findViewById(R.id.showCode);
        code = findViewById(R.id.code);
        changeCode = findViewById(R.id.changeCode);
        showPass = findViewById(R.id.showPass);
        showPic = findViewById(R.id.showPic);
        back = findViewById(R.id.back);
        op = findViewById(R.id.op);
        np = findViewById(R.id.np);
        np2 = findViewById(R.id.np2);
        cp = findViewById(R.id.cp);
        pic = findViewById(R.id.picture);
        passPic = findViewById(R.id.passPicture);
        changePic = findViewById(R.id.changePicture);
        LPIC = findViewById(R.id.LPIC);LPASS = findViewById(R.id.LPASS);LCODE = findViewById(R.id.LCODE);
        textInputLayout1 = findViewById(R.id.textInputLayout1);
        textInputLayout2 = findViewById(R.id.textInputLayout2);
        textInputLayout3 = findViewById(R.id.textInputLayout3);
        textInputLayout4 = findViewById(R.id.textInputLayout4);
        textInputLayout1.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        textInputLayout2.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        textInputLayout3.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        textInputLayout4.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
    }
    private void listener() {
        showCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LPASS.setVisibility(View.GONE);
                LPIC.setVisibility(View.GONE);
                LCODE.setVisibility(View.VISIBLE);
            }
        });
        showPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LPIC.setVisibility(View.VISIBLE);
                LPASS.setVisibility(View.GONE);
                LCODE.setVisibility(View.GONE);
            }
        });
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LPASS.setVisibility(View.VISIBLE);
                LPIC.setVisibility(View.GONE);
                LCODE.setVisibility(View.GONE);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pic.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                pic.setImageBitmap(null);
                pic.setImageDrawable(null);
                Drawable drawable = ContextCompat.getDrawable(settings.this, R.drawable.person);
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
        changePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passPic.setText(passPic.getText().toString().trim());
                pass = passPic.getText().toString();
                if(pic.getDrawable()==null){
                    Toast.makeText(settings.this, "Select an Image", Toast.LENGTH_SHORT).show();
                }
                else if(pass.equals("")){
                    passPic.setError("Required");
                }
                else{
                    rel.setVisibility(View.VISIBLE);
                    Drawable drawable = pic.getDrawable();
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    new settPicToArray(settings.this,settings.this).execute(bitmap);
                }
            }
        });
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                op.setText(op.getText().toString().trim());
                np.setText(np.getText().toString().trim());
                np2.setText(np2.getText().toString().trim());
                if(op.getText().toString().equals("")){
                    op.setError("Required");
                }
                else if(np.getText().toString().equals("")){
                    np.setError("Required");
                }
                else if(np.getText().toString().length()<6){
                    np.setError("Password must be at least 6 characters");
                }
                else if(!isInputValid(np.getText().toString())){
                    np.setError("Password must have at least 1 UPPERCASE, 1 lowercase, 1 special character and 1 number");
                }
                else if(!np.getText().toString().equals(np2.getText().toString())){
                    np2.setError("Incorrect Password. Password and Confirm Password must be the same");
                }
                else{
                    rel.setVisibility(View.VISIBLE);
                    cp.setEnabled(false);
                    op.setEnabled(false);
                    np.setEnabled(false);
                    np2.setEnabled(false);
                    showPic.setEnabled(false);
                    showPass.setEnabled(false);
                    new settingsAsync(settings.this).execute(UserPage.id,op.getText().toString(),np.getText().toString());
                }
                }
        });
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
                new forgotPasswordAsync(settings.this, new forgotPasswordAsync.resultListener() {
                    @Override
                    public void onResult(String s) {
                        rel.setVisibility(View.GONE);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String response = jsonObject.getString("response");
                            if(response.equals("success")){
                                code.setText(newCode);
                                Toast.makeText(settings.this, "Reset Code updated", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(settings.this, "Server Error.Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute("new",UserPage.id,newCode);
            }
        });
    }
    public void send(byte[] bytes){
        showPic.setEnabled(false);
        showPass.setEnabled(false);
        pic.setEnabled(false);
        passPic.setEnabled(false);
        changePic.setEnabled(false);
        new changePicAsync(settings.this).execute(UserPage.id,pass,Base64.encodeToString(bytes, Base64.DEFAULT));
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
                Intent intent = new Intent(settings.this, UserReport.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(settings.this, com.example.capstone.Messaging.chat.class);
                intent.putExtra("name","Admin");
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(settings.this, UserPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(settings.this, MainActivity.class);
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
                                    UriToBitmapTask task = new UriToBitmapTask(settings.this, new UriToBitmapTask.OnBitmapLoadedListener() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap) {
                                            pic.setImageBitmap(bitmap);
                                            pic.setBackground(null);
                                        }
                                    });
                                    task.execute(resizedAndConvertedUri);
                                }
                                else{
                                    Toast.makeText(settings.this, "Image size is too large. Please choose a smaller image.", Toast.LENGTH_SHORT).show();
                                    pic.setImageBitmap(null);pic.setImageDrawable(null);
                                    Drawable drawable = ContextCompat.getDrawable(settings.this, R.drawable.person);
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
    @Override
    public void onBackPressed() {
        finish();
    }
}