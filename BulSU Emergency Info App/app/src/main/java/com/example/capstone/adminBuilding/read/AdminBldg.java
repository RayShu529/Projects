package com.example.capstone.adminBuilding.read;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.capstone.IncidentReport.AdminReports;
import com.example.capstone.adminBuilding.restore.restoreBuilding;
import com.example.capstone.R;
import com.example.capstone.admin.AdminChat;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.adminBuilding.add.Buildings;
import com.example.capstone.adminBuilding.edit.editBldg;
import com.example.capstone.login.MainActivity;
import com.example.capstone.user.UserPage;

public class AdminBldg extends AppCompatActivity {
    ImageView back;
    TextView tv;
    Button add,restore;
    adminAdapter adapter;
    RecyclerView recyclerView;
    String[] names = new String[0];
    RelativeLayout rel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bldg);
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
        readBldg read = new readBldg(AdminBldg.this);
        read.execute();
        rel.setVisibility(View.VISIBLE);
    }
    public void update(String[] bldgNames) {
            names = bldgNames;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new adminAdapter(names);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            tv.setText("");
    }

    private void set() {
        restore = findViewById(R.id.restore);
        tv = findViewById(R.id.tv);
        back = findViewById(R.id.back);
        add = findViewById(R.id.add);
        recyclerView = findViewById(R.id.rec);
    }
    private void listeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminBldg.this, Buildings.class);
                startActivityForResult(i,1);
            }
        });
        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminBldg.this, restoreBuilding.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            readBldg read = new readBldg(AdminBldg.this);
            read.execute();
        }
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
                Intent intent = new Intent(AdminBldg.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(AdminBldg.this, AdminChat.class);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(AdminBldg.this,AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminBldg.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(AdminBldg.this, MainActivity.class);
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
    protected void onResume() {
        super.onResume();
        readBldg read = new readBldg(AdminBldg.this);
        read.execute();
        rel.setVisibility(View.VISIBLE);
    }
}
