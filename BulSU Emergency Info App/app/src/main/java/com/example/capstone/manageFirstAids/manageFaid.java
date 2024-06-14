package com.example.capstone.manageFirstAids;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.util.Log;
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

import com.example.capstone.ImageResizeAndConvertTask;
import com.example.capstone.IncidentReport.AdminReports;
import com.example.capstone.R;
import com.example.capstone.admin.AdminChat;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.adminBuilding.edit.UriToBitmapTask;
import com.example.capstone.adminSetting.BitToArr;
import com.example.capstone.adminSetting.adminSett;
import com.example.capstone.adminSetting.updateAsync;
import com.example.capstone.login.MainActivity;
import com.example.capstone.register.RegPage;
import com.google.android.material.textfield.TextInputLayout;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class manageFaid extends AppCompatActivity {
    String action, password,injury,admin;
ImageView back,addPIC;
Button showadd,addB,delB,showdel;
LinearLayout addL,delL;
EditText addN,addP,delP;
TextInputLayout textInputLayout1,textInputLayout2;
Spinner delN;
TextView delN1;
int checker;
RelativeLayout rel;
    final int MAX_FILE_SIZE = 490;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_faid);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        admin = sharedPreferences.getString("name", "");
        findID();
        LISTENERS();
        sync();

    }

    private void findID() {
        rel = findViewById(R.id.rel);rel.setVisibility(View.VISIBLE);
        back = findViewById(R.id.back);
        addPIC = findViewById(R.id.addPIC);
        showadd = findViewById(R.id.showadd);
        addB = findViewById(R.id.addB);
        addN = findViewById(R.id.addN);
        addP = findViewById(R.id.addP);
        addL = findViewById(R.id.addL);
        textInputLayout1 = findViewById(R.id.textInputLayout1);textInputLayout1.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        delB = findViewById(R.id.delB);showdel = findViewById(R.id.showdel);
        delL = findViewById(R.id.delL);
        delP = findViewById(R.id.delP);
        delN = findViewById(R.id.delN);delN1 = findViewById(R.id.delN1);
        textInputLayout2 = findViewById(R.id.textInputLayout2);textInputLayout2.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        showadd.setEnabled(false);
        showdel.setEnabled(false);
    }

    private void LISTENERS() {
        addPIC.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Drawable drawable = ContextCompat.getDrawable(manageFaid.this, R.drawable.addnew);
                addPIC.setBackground(drawable);
                addPIC.setImageBitmap(null);
                addPIC.setImageDrawable(null);
                return true;
            }
        });
        addPIC.setOnClickListener(new View.OnClickListener() {
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
        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addN.getText().toString().trim().toUpperCase();
                String pass = addP.getText().toString().trim();
                addN.setText(name);
                addP.setText(pass);
                if(addPIC.getDrawable()==null){
                    Toast.makeText(manageFaid.this, "Select an Image!", Toast.LENGTH_SHORT).show();
                }
                else if (name.equals("")){
                    addN.setError("Required");
                }
                else if (pass.equals("")){
                    addP.setError("Required");
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(manageFaid.this);
                    builder.setTitle("Add First Aid")
                            .setMessage("Adding First Aid for: "+name+"\nIf the name of the First Aid is already in the database, it will automatically overwrite.")
                            .setCancelable(true)
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    addB.setEnabled(false);addPIC.setEnabled(false);
                                    addN.setEnabled(false);addP.setEnabled(false);
                                    rel.setVisibility(View.VISIBLE);
                                    Drawable drawable = addPIC.getDrawable();
                                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                                    Bitmap bitmap = bitmapDrawable.getBitmap();
                                    action = "add";password = pass; injury = name;
                                    new PictureToArray(manageFaid.this,manageFaid.this).execute(bitmap);
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
        delB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker==0){

                    String name = delN.getSelectedItem().toString();
                    String pass = delP.getText().toString().trim();
                    if (pass.equals("")){
                        delP.setError("Required");
                    }
                    else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(manageFaid.this);
                        builder.setTitle("Archive First Aid")
                                .setMessage("The First Aid for "+name+" will be archived.\nAre you sure about that?")
                                .setCancelable(true)
                                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        delB.setEnabled(false);delN.setEnabled(false);
                                        delP.setEnabled(false);rel.setVisibility(View.VISIBLE);
                                        action = "del";password = pass; injury = name;
                                        new firstAidAsync(manageFaid.this).execute(action,injury,password,"",admin);
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
                    Toast.makeText(manageFaid.this, "No First Aid to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
        showadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPIC.setImageBitmap(null);addPIC.setImageDrawable(null);addN.setText("");addP.setText("");
                if(addL.getVisibility()==v.VISIBLE){
                    addL.setVisibility(View.GONE);
                    delL.setVisibility(View.GONE);
                }
                else{
                    addL.setVisibility(View.VISIBLE);
                    delL.setVisibility(View.GONE);
                }
            }
        });
        showdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(delL.getVisibility()==v.VISIBLE){
                    addL.setVisibility(View.GONE);
                    delL.setVisibility(View.GONE);
                }
                else{
                    delL.setVisibility(View.VISIBLE);
                    addL.setVisibility(View.GONE);
                }
            }
        });
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
                            addPIC.setImageBitmap(bitmap);addPIC.setBackground(null);
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
                                    UriToBitmapTask task = new UriToBitmapTask(manageFaid.this, new UriToBitmapTask.OnBitmapLoadedListener() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap) {
                                            addPIC.setImageBitmap(bitmap);
                                            addPIC.setBackground(null);
                                        }
                                    });
                                    task.execute(resizedAndConvertedUri);
                                }
                                else{
                                    Toast.makeText(manageFaid.this, "Image size is too large. Please choose a smaller image.", Toast.LENGTH_SHORT).show();
                                    addPIC.setImageBitmap(null);addPIC.setImageDrawable(null);
                                    Drawable drawable = ContextCompat.getDrawable(manageFaid.this, R.drawable.addnew);
                                    addPIC.setBackground(drawable);
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
public void send(byte[] bytes){
    new firstAidAsync(manageFaid.this).execute(action,injury,password, Base64.encodeToString(bytes, Base64.DEFAULT),admin);
}
public void sync(){
    new firstAidAsync(manageFaid.this).execute("read","","", "","");
}
public void getList(ArrayList<String> list){
    if(list.size()==0){
        delN.setVisibility(View.GONE);
        delN1.setVisibility(View.VISIBLE);
        checker=1;
    }
    else{
        checker=0;
        delN.setVisibility(View.VISIBLE);delN1.setVisibility(View.GONE);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
// Set the dropdown layout style (optional)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Set the adapter for the Spinner
        delN.setAdapter(adapter);
    }
}
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
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
                Intent intent = new Intent(manageFaid.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(manageFaid.this, AdminChat.class);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(manageFaid.this, AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(manageFaid.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(manageFaid.this, MainActivity.class);
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