package com.example.capstone.user;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capstone.IncidentReport.UserReport;
import com.example.capstone.Messaging.chat;
import com.example.capstone.MyForegroundService;
import com.example.capstone.R;
import com.example.capstone.login.MainActivity;
import com.example.capstone.user.evacPlan.userBldg;
import com.example.capstone.user.firstAid.fAid;
import com.example.capstone.user.hotline.call;
import com.example.capstone.user.settings.settings;

public class UserPage extends AppCompatActivity {
    private int[] images = { R.drawable.gobagtips,  R.drawable.firetips,R.drawable.floodtips, R.drawable.eqtips };
    private ViewPager viewPager;
    TextView welcome;
    Button hotline,firstAid,evacPath,settings;
    Button helpbutt;
    RelativeLayout rel2;
    public static String id="";
    public static String name="";
    Bitmap bitmap;
    float degreesToRotate = 90.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        set();
        listener();
        ActionBar actionBar = getSupportActionBar();
        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");
        name = sharedPreferences.getString("name", "");
        welcome.setText(name);

    }
    private void set() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        welcome = findViewById(R.id.welcome);

        hotline = findViewById(R.id.hotline);
        firstAid = findViewById(R.id.firstAid);
        evacPath = findViewById(R.id.evacPath);
        settings = findViewById(R.id.settings);
        ImageSliderAdapter adapter = new ImageSliderAdapter(this, images);
        viewPager.setAdapter(adapter);
        handler.postDelayed(runnable, 3000);
    }
    private void listener() {

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Drawable drawable = ContextCompat.getDrawable(UserPage.this,images[viewPager.getCurrentItem()]);
                    Bitmap bm = drawableToBitmap(drawable);
                    if(bm.getHeight()<bm.getWidth()){
                        bitmap= rotateBitmap(bm, degreesToRotate);
                    }
                    else{
                        bitmap=bm;
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserPage.this);
                    View customLayout = getLayoutInflater().inflate(R.layout.customalert, null);
                    ImageView imageView = customLayout.findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);
                    builder.setView(customLayout);
                    builder.setCancelable(true);
                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                return false;
            }
        });

        evacPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserPage.this, userBldg.class);
                startActivity(i);
            }
        });
        firstAid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserPage.this, fAid.class);
                startActivity(i);
            }
        });
        hotline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserPage.this,call.class);
                startActivity(i);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserPage.this, settings.class);
                startActivity(i);
            }
        });

    }
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            int currentItem = viewPager.getCurrentItem();
            int totalItems = viewPager.getAdapter().getCount();
            int nextItem = currentItem + 1;
            if(nextItem==totalItems){
                viewPager.setCurrentItem(0);
            }
            else{
                viewPager.setCurrentItem(nextItem);
            }
            handler.postDelayed(this, 5000);
        }
    };
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);

        // Retrieve the button item from the menu
        MenuItem chat = menu.findItem(R.id.nav_chat);
        MenuItem home = menu.findItem(R.id.nav_home);

        MenuItem logout = menu.findItem(R.id.nav_logout);
        // Set a click listener for the button
        MenuItem report = menu.findItem(R.id.report);
        report.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(UserPage.this, UserReport.class);
                startActivity(intent);
                return true;
            }
        });
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(UserPage.this, chat.class);
                intent.putExtra("name","Admin");
                startActivity(intent);
                return true;
            }
        });
        home.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(UserPage.this,UserPage.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            }
        });
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserPage.this);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to Logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("id", "");
                        editor.putString("name", "");
                        editor.apply();
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
    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
    public Bitmap rotateBitmap(Bitmap source, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }


}