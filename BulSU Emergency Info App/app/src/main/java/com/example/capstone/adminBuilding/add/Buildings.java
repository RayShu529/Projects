package com.example.capstone.adminBuilding.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.capstone.ImageResizeAndConvertTask;
import com.example.capstone.IncidentReport.AdminReports;
import com.example.capstone.R;
import com.example.capstone.admin.AdminChat;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.login.MainActivity;
import com.example.capstone.user.UserPage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Buildings extends AppCompatActivity {

    ImageView back;
    EditText bldgname;
    ImageView first, second, third, fourth;
    Button addbldg;
    Uri[] uriArray = new Uri[4];
    final int MAX_FILE_SIZE = 490;
int a;
RelativeLayout rel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
        set();
        listeners();
        ActionBar actionBar = getSupportActionBar();
        rel = findViewById(R.id.rel);
        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void set() {
        back = findViewById(R.id.back);
        bldgname = findViewById(R.id.bldgname);
        first = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        addbldg = findViewById(R.id.addbldg);
        second.setEnabled(false);
        third.setEnabled(false);
        fourth.setEnabled(false);
    }
    private void listeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        first.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                uriArray[0] = null;
                first.setImageURI(null);
                return true;
            }
        });
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        second.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                uriArray[1] = null;
                second.setImageURI(null);
                return true;
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });
        third.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                uriArray[2] = null;
                third.setImageURI(null);
                return true;
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 3);
            }
        });
        fourth.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                uriArray[3] = null;
                fourth.setImageURI(null);
                return true;
            }
        });
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 4);
            }
        });
        addbldg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bldgname.setText(bldgname.getText().toString().trim());
                if(bldgname.getText().toString().equals("")){
                    bldgname.setError("Input Building Name");
                }
                else if(uriArray[0] == null){
                    Toast.makeText(Buildings.this, "Set 1st Floor plan", Toast.LENGTH_SHORT).show();
                }
                else if (uriArray[1] ==null &&uriArray[2] !=null || uriArray[1] ==null &&uriArray[3] !=null){
                    Toast.makeText(Buildings.this, "Add 2nd Floor plan", Toast.LENGTH_SHORT).show();
                }
                else if (uriArray[2] ==null &&uriArray[3] !=null){
                    Toast.makeText(Buildings.this, "Add 3rd Floor plan", Toast.LENGTH_SHORT).show();
                }
                else{
                    rel.setVisibility(View.VISIBLE);
                    bldgname.setEnabled(false);
                    addbldg.setEnabled(false);
                    first.setEnabled(false);
                    second.setEnabled(false);
                    third.setEnabled(false);
                    fourth.setEnabled(false);
                    bldgCheck check = new bldgCheck(Buildings.this);
                    check.execute(bldgname.getText().toString());
                }
            }
        });
    }

    public void send() {
        rel.setVisibility(View.VISIBLE);
        bldgname.setEnabled(false);
        addbldg.setEnabled(false);
        first.setEnabled(false);
        second.setEnabled(false);
        third.setEnabled(false);
        fourth.setEnabled(false);
        for (int i = 0;i<4;i++){
            if (uriArray[i]==null){
                break;
            }
            String floor = String.valueOf(i+1);
            imgToByte task = new imgToByte(Buildings.this, new imgToByte.OnByteArrayConvertedListener() {
                @Override
                public void onByteArrayConverted(byte[] bytes) {
                    new buildingsAsync(Buildings.this).execute(bldgname.getText().toString(),floor, Base64.encodeToString(bytes, Base64.DEFAULT));
                }
            });
            task.execute(uriArray[i]);

        }
        Toast.makeText(this, "Building saved successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            try {
                InputStream  inputStream = getContentResolver().openInputStream(data.getData());
                int file_size = inputStream.available()/1024;
                if (file_size <= MAX_FILE_SIZE) {
                    inputStream.close();
                    uriArray[0] = data.getData();
                    first.setImageURI(uriArray[0]);
                    second.setEnabled(true);
                } else {
                    inputStream.close();
                    resize(data.getData(),"first");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            try {
                InputStream  inputStream = getContentResolver().openInputStream(data.getData());
                int file_size = inputStream.available()/1024;
                if (file_size <= MAX_FILE_SIZE) {
                    inputStream.close();
                    uriArray[1] = data.getData();
                    second.setImageURI(uriArray[1]);
                    third.setEnabled(true);

                } else {
                    inputStream.close();
                    resize(data.getData(),"second");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == 3 && resultCode == RESULT_OK && data != null) {
            try {
                InputStream  inputStream = getContentResolver().openInputStream(data.getData());
                int file_size = inputStream.available()/1024;
                if (file_size <= MAX_FILE_SIZE) {
                    inputStream.close();
                    uriArray[2] = data.getData();
                    third.setImageURI(uriArray[2]);
                    fourth.setEnabled(true);
                } else {
                    inputStream.close();
                    resize(data.getData(),"third");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (requestCode == 4 && resultCode == RESULT_OK && data != null) {
            try {
                InputStream  inputStream = getContentResolver().openInputStream(data.getData());
                int file_size = inputStream.available()/1024;
                if (file_size <= MAX_FILE_SIZE) {
                    inputStream.close();
                    uriArray[3] = data.getData();
                    fourth.setImageURI(uriArray[3]);
                } else {
                    inputStream.close();
                    resize(data.getData(),"fourth");
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void resize(Uri uri, String num){
        new ImageResizeAndConvertTask(this, new ImageResizeAndConvertTask.OnImageResizedAndConvertedListener() {
            @Override
            public void onImageResizedAndConverted(Uri resizedAndConvertedUri) {
                try{
                    InputStream  inputStream = getContentResolver().openInputStream(resizedAndConvertedUri);
                    int file_size = inputStream.available()/1024;
                    if (file_size <= MAX_FILE_SIZE) {
                        if(num.equals("first")){
                            uriArray[0] = uri;
                            first.setImageURI(uriArray[0]);
                            second.setEnabled(true);
                        }
                        else if(num.equals("second")){
                            uriArray[1] = uri;
                            second.setImageURI(uriArray[1]);
                            third.setEnabled(true);
                        }
                        else if(num.equals("third")){
                            uriArray[2] = uri;
                            third.setImageURI(uriArray[2]);
                            fourth.setEnabled(true);
                        }
                        else if(num.equals("fourth")){
                            uriArray[3] = uri;
                            fourth.setImageURI(uriArray[3]);
                        }
                    }
                    else{
                        if(num.equals("first")){
                            first.setImageURI(null);first.setImageDrawable(null);uriArray[0] = null;first.setImageBitmap(null);

                        }
                        else if(num.equals("second")){
                            second.setImageURI(null);second.setImageDrawable(null);uriArray[1] = null;second.setImageBitmap(null);

                        }
                        else if(num.equals("third")){
                            third.setImageURI(null);third.setImageDrawable(null);uriArray[2] = null;third.setImageBitmap(null);

                        }
                        else if(num.equals("fourth")){
                            fourth.setImageURI(null);fourth.setImageDrawable(null);uriArray[3] = null;fourth.setImageBitmap(null);
                        }
                        Toast.makeText(Buildings.this, "Image size is too large. Please choose a smaller image.", Toast.LENGTH_SHORT).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).execute(uri);
    }
    @Override
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
                Intent intent = new Intent(Buildings.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Buildings.this, AdminChat.class);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(Buildings.this,AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Buildings.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(Buildings.this, MainActivity.class);
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
}
