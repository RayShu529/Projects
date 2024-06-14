package com.example.capstone.adminBuilding.edit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.ImageResizeAndConvertTask;
import com.example.capstone.IncidentReport.AdminReports;
import com.example.capstone.R;
import com.example.capstone.admin.AdminChat;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.adminBuilding.add.Buildings;
import com.example.capstone.adminBuilding.add.bldgCheck;
import com.example.capstone.adminBuilding.add.buildingsAsync;
import com.example.capstone.adminBuilding.add.imgToByte;
import com.example.capstone.login.MainActivity;
import com.example.capstone.user.UserPage;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class editBldg extends AppCompatActivity {
    Drawable[] drawables = new Drawable[4];
    ImageView back;
    RelativeLayout rel;
    TextView bldgname;
    ImageView first, second, third, fourth;
    Button edit;
    String name;
    int ctr=1,vis;
    final int MAX_FILE_SIZE = 490;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bldg);
        set();
        listeners();
        ActionBar actionBar = getSupportActionBar();
        rel = findViewById(R.id.rel);
        rel.setVisibility(View.VISIBLE);
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
        edit = findViewById(R.id.edit);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        editAsync e = new editAsync(editBldg.this);
        e.execute(name);
        bldgname.setText(name);
        edit.setEnabled(false);
        first.setEnabled(false);
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
                first.setImageBitmap(null);
                first.setImageDrawable(null);
                return true;
            }
        });
        second.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                second.setImageBitmap(null);
                second.setImageDrawable(null);
                return true;
            }
        });
        third.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                third.setImageBitmap(null);
                third.setImageDrawable(null);
                return true;
            }
        });
        fourth.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fourth.setImageBitmap(null);
                fourth.setImageDrawable(null);
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
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
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
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 4);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(first.getDrawable() == null){
                    Toast.makeText(editBldg.this, "Set 1st Floor plan", Toast.LENGTH_SHORT).show();
                }
                else if (second.getDrawable() ==null &&third.getDrawable() !=null || second.getDrawable() ==null &&fourth.getDrawable() !=null){
                    Toast.makeText(editBldg.this, "Add 2nd Floor plan", Toast.LENGTH_SHORT).show();
                }
                else if (third.getDrawable() ==null &&fourth.getDrawable() !=null){
                    Toast.makeText(editBldg.this, "Add 3rd Floor plan", Toast.LENGTH_SHORT).show();
                    }
                else if(samePic()){
                    Toast.makeText(editBldg.this, "Change at least one floor plan.", Toast.LENGTH_SHORT).show();
                }
                else {
                    rel.setVisibility(View.VISIBLE);
                    edit.setEnabled(false);
                    first.setEnabled(false);
                    second.setEnabled(false);
                    third.setEnabled(false);
                    fourth.setEnabled(false);
                    Bitmap[] bitmaps = new Bitmap[4];
                    Drawable[] drawable = {first.getDrawable(), second.getDrawable(), third.getDrawable(), fourth.getDrawable()};
                    for (int i = 0; i < 4; i++) {
                        if (drawable[i] instanceof BitmapDrawable) {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable[i];
                            bitmaps[i] = bitmapDrawable.getBitmap();
                        }
                    }
                    for(int i = 0;i<4; i++){
                        if(bitmaps[i]!=null){
                            new BitmapToByteArrayTask(editBldg.this,editBldg.this).execute(bitmaps[i]);
                        }
                    }
                }
            }
        });
    }
    private boolean samePic(){
        int i=1;
        for(int q = 0;q<4;q++){

            if(q==0&&drawables[0]!=first.getDrawable()){
                i=0;break;
            }
            if(q==1&&drawables[1]!=second.getDrawable()){
                i=0;break;
            }
            if(q==2&&drawables[2]!=third.getDrawable()){
                i=0;break;
            }
            if(q==3&&drawables[3]!=fourth.getDrawable()){
                i=0;break;
            }
        }
        if(i==0){
            return false;
        }
        else{
            return true;
        }

    }
    public void send(byte[] bytes){
        if(ctr==1){
            new clearPic(editBldg.this).execute(name);
        }
        String floor = String.valueOf(ctr);

        new update(editBldg.this).execute(name,floor,Base64.encodeToString(bytes, Base64.DEFAULT));
        ctr++;
    }

    public void getFloors(String a) {
        int ctr = Integer.parseInt(a);
        vis = ctr;
        for (int x = 0; x<ctr;x++){
            show pic = new show(editBldg.this);
            pic.execute(name,String.valueOf(x+1));
        }
    }
    public void getPic(String a, int b) {
        byte[] bytes = Base64.decode(a, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if(b==1){
            first.setImageBitmap(bmp);
            drawables[0] = first.getDrawable();
        }
        else if(b==2){
            second.setImageBitmap(bmp);
            drawables[1] = second.getDrawable();
        }
        else if(b==3){
            third.setImageBitmap(bmp);
            drawables[2] = third.getDrawable();
        }
        else if(b==4){
            fourth.setImageBitmap(bmp);
            drawables[3] = fourth.getDrawable();
        }
        if(b==vis){
            rel.setVisibility(View.GONE);
            edit.setEnabled(true);
            first.setEnabled(true);
            second.setEnabled(true);
            third.setEnabled(true);
            fourth.setEnabled(true);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
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
                            first.setImageBitmap(bitmap);
                        }
                    });
                    task.execute(data.getData());
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
                    UriToBitmapTask task = new UriToBitmapTask(this, new UriToBitmapTask.OnBitmapLoadedListener() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap) {
                            second.setImageBitmap(bitmap);
                        }
                    });
                    task.execute(data.getData());
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
                    UriToBitmapTask task = new UriToBitmapTask(this, new UriToBitmapTask.OnBitmapLoadedListener() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap) {
                            third.setImageBitmap(bitmap);
                        }
                    });
                    task.execute(data.getData());
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
                    UriToBitmapTask task = new UriToBitmapTask(this, new UriToBitmapTask.OnBitmapLoadedListener() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap) {
                            fourth.setImageBitmap(bitmap);
                        }
                    });
                    task.execute(data.getData());
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
                        inputStream.close();
                        UriToBitmapTask task = new UriToBitmapTask(editBldg.this, new UriToBitmapTask.OnBitmapLoadedListener() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap) {
                                if(num.equals("first")){
                                    first.setImageBitmap(bitmap);
                                }
                                else if(num.equals("second")){
                                    second.setImageBitmap(bitmap);
                                }
                                else if(num.equals("third")){
                                    third.setImageBitmap(bitmap);
                                }
                                else if(num.equals("fourth")){
                                    fourth.setImageBitmap(bitmap);
                                }
                            }
                        });
                        task.execute(resizedAndConvertedUri);
                    }
                    else{
                        if(num.equals("first")){
                            first.setImageBitmap(null);
                            first.setImageDrawable(null);
                        }
                        else if(num.equals("second")){
                            second.setImageBitmap(null);
                            second.setImageDrawable(null);
                        }
                        else if(num.equals("third")){
                            third.setImageBitmap(null);
                            third.setImageDrawable(null);
                        }
                        else if(num.equals("fourth")){
                            fourth.setImageBitmap(null);
                            fourth.setImageDrawable(null);
                        }
                        Toast.makeText(editBldg.this, "Image size is too large. Please choose a smaller image.", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(editBldg.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(editBldg.this, AdminChat.class);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(editBldg.this,AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                AlertDialog.Builder builder = new AlertDialog.Builder(editBldg.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(editBldg.this, MainActivity.class);
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