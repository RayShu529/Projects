package com.example.capstone.IncidentReport;

import androidx.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.capstone.R;
import com.example.capstone.admin.AdminChat;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.adminSetting.adminSett;
import com.example.capstone.login.MainActivity;

import java.util.ArrayList;

public class AdminReports extends AppCompatActivity {
RecyclerView rec;
RelativeLayout rel;
TextView tv;
ImageView back;
AdminReportsAdapter adapter;
    ArrayList<String> caseNum = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> emergency = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reports);
        findID();
        ActionBar actionBar = getSupportActionBar();
        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void findID() {
         rec = findViewById(R.id.rec);
        rel = findViewById(R.id.rel);
        tv = findViewById(R.id.tv);
        back = findViewById(R.id.back);
        async();
        listeners();
    }
public void async(){
        rel.setVisibility(View.VISIBLE);
        new AdminReportsAsync(AdminReports.this).execute("read");
}
    private void listeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
public void populate(ArrayList<String> caseNum, ArrayList<String> date, ArrayList<String> emergency){
        if(caseNum.size()==0){
            tv.setVisibility(View.VISIBLE);
            tv.setText("No Reports");
        }
        else{
            rel.setVisibility(View.GONE);
            this.caseNum = caseNum;this.date = date;this.emergency = emergency;
            rec.setLayoutManager(new LinearLayoutManager(this));
            adapter = new AdminReportsAdapter(caseNum,date,emergency);
            rec.setAdapter(adapter);
        }
}
    @Override
    public void onBackPressed() {
        finish();
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
                Intent intent = new Intent(AdminReports.this, AdminReports.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(AdminReports.this, AdminChat.class);
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(AdminReports.this, AdminPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(intent);
                finish();
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminReports.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
                        Intent i = new Intent(AdminReports.this, MainActivity.class);
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