package com.example.capstone.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.capstone.IncidentReport.AdminReports;
import com.example.capstone.Messaging.chat;
import com.example.capstone.R;
import com.example.capstone.adminBuilding.read.adminAdapter;
import com.example.capstone.login.MainActivity;
import com.example.capstone.user.UserPage;

import java.util.ArrayList;

public class AdminChat extends AppCompatActivity {
TextView tv;
RelativeLayout rel;
ImageView back;
RecyclerView rec;
AdminAdapter adapter;
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<Bitmap> pic = new ArrayList<>();
    int ctr = 0,ctr2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chat);
        set();
        listener();
        ActionBar actionBar = getSupportActionBar();
        UserPage.id="";
        // Set Logo Icon
        rel = findViewById(R.id.rel);
        rel.setVisibility(View.VISIBLE);
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        new AdminAsync(AdminChat.this).execute();
    }
    public void update(String[] id){
        for(int i = 0;i<id.length;i++){
            this.id.add(id[i]);
        }
        for(int i = 0;i<id.length;i++){
            new AdminChatName(AdminChat.this).execute(id[i]);
        }
    }
    public void checkName(String name){
        this.name.add(name);
        ctr++;
        if(ctr==id.size()){
            for(int i = 0;i<id.size();i++){
                new AdminChatPic(AdminChat.this).execute(id.get(i));
            }
        }
    }
    public void checkPic(String pic){
        if(pic.equals("")){
            Bitmap bitmap = BitmapFactory.decodeResource(AdminChat.this.getResources(), R.drawable.bulsu);
            this.pic.add(bitmap);
        }
        else{
            try {
                byte[] bytes = Base64.decode(pic, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                this.pic.add(bmp);
            }catch (Exception e){
                Bitmap bitmap = BitmapFactory.decodeResource(AdminChat.this.getResources(), R.drawable.bulsu);
                this.pic.add(bitmap);
            }
        }
        ctr2++;
        if(ctr2==id.size()){
            setUserInfo();
            rel.setVisibility(View.GONE);
        }
    }
    public void show(){
        tv.setVisibility(View.VISIBLE);
        tv.setText("No Messages");
    }
    public void setUserInfo(){
        rec.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminAdapter(this.id,this.name,this.pic,AdminChat.this);
        rec.setAdapter(adapter);
        tv.setText("");
    }
    private void set() {
        tv = findViewById(R.id.tv);
        back = findViewById(R.id.back);
        rec = findViewById(R.id.rec);

    }
    private void listener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                Intent intent = new Intent(AdminChat.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(AdminChat.this,AdminChat.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(AdminChat.this,AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminChat.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(AdminChat.this, MainActivity.class);
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
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}