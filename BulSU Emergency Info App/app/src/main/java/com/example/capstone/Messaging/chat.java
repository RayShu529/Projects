package com.example.capstone.Messaging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.BulsuPlan.BulsuEvacPlan;
import com.example.capstone.ImageResizeAndConvertTask;
import com.example.capstone.R;
import com.example.capstone.admin.AdminChat;
import com.example.capstone.admin.AdminPage;
import com.example.capstone.adminBuilding.edit.UriToBitmapTask;
import com.example.capstone.adminNotif.Maps;
import com.example.capstone.adminNotif.sendNotif;
import com.example.capstone.login.MainActivity;
import com.example.capstone.user.UserPage;
import com.example.capstone.user.evacPlan.userAdapter;
import com.example.capstone.user.firstAid.fAid;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class chat extends AppCompatActivity {
TextView receiver,receiver2;
ImageView back,pic;
EditText msg;
Button send,loc,cam;
RelativeLayout rel;
    LatLng latLng = null;
    Bitmap bitmap;
    float degreesToRotate = 90.0f;
int c=1;
    RecyclerView rec;
    chatAdapter adapter;
    ArrayList<String> messages = new ArrayList<>();
    ArrayList<String> sender = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    String dateTime;
    LinearLayoutManager layoutManager;
    private Timer timer;
    int Position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        set();
        listener();
rel = findViewById(R.id.rel);
rel.setVisibility(View.VISIBLE);
        cam = findViewById(R.id.cam);
        ActionBar actionBar = getSupportActionBar();

        // Set Logo Icon
        if (actionBar != null) {
            actionBar.setLogo(R.drawable.bsu);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        if(getIntent().hasExtra("name")){
            receiver.setText(getIntent().getStringExtra("name"));
            if(getIntent().getStringExtra("name").equals("Admin")){
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                UserPage.id = sharedPreferences.getString("id", "");
                AlertDialog.Builder builder = new AlertDialog.Builder(chat.this);
                builder.setTitle("Instructions")
                        .setMessage("When messaging the Admin, it is recommended to send the location first followed by the situation.");
                // Add buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform an action when the "OK" button is clicked
                    }
                }).setCancelable(true);
                // Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                start();
                setImage("");

                cam.setVisibility(View.VISIBLE);
                cam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Check if CAMERA permission is granted
                        if (ContextCompat.checkSelfPermission(chat.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // Request CAMERA permission
                            ActivityCompat.requestPermissions(chat.this, new String[]{Manifest.permission.CAMERA}, 69);
                        } else {
                            // Permission is already granted, open the camera
                            openCamera();
                        }

                    }
                });
            }
            else{
                new getNamePic(chat.this).execute(receiver.getText().toString());
                new getPicture(chat.this).execute(receiver.getText().toString());
            }
        }
    }
    public void start(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateDateTime();
            }
        }, 0, 1000); // Update every 1000 milliseconds (1 second)
        handler.post(myRunnable);
    }
    public void messageUpdate(String a){
        if(UserPage.id.equals("")){

            new chatAsync(chat.this).execute("","","",a,"");
        }
        else{
            new chatAsync(chat.this).execute("","","",UserPage.id,"");
        }
    }
    private  void updateDateTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so adding 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        dateTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }
    private void set() {

        pic = findViewById(R.id.pic);
        receiver2 = findViewById(R.id.name2);
        loc = findViewById(R.id.loc);
        loc.setEnabled(false);
        rec = findViewById(R.id.rec);
        receiver = findViewById(R.id.name);
        back = findViewById(R.id.back);
        msg = findViewById(R.id.msg);
        send = findViewById(R.id.send);
        send.setEnabled(false);
        rec.setLayoutManager(new LinearLayoutManager(this));
        adapter = new chatAdapter(messages,time,sender,getIntent().getStringExtra("name"),chat.this);
        rec.setAdapter(adapter);
         layoutManager = (LinearLayoutManager) rec.getLayoutManager();
    }
    private void listener() {
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPointIntent = new Intent(chat.this, Maps.class);
                startActivityForResult(pickPointIntent, 10);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg.setText(msg.getText().toString().trim().replace("{", "").replace("}", ""));
                if(msg.getText().toString().replace(" ","").equals("Image sent. Click to view Image.")){
                    msg.setText("");
                }
                if(msg.getText().toString().equals("")){
                    msg.setError("Enter message");
                }
                else {
                    Position = messages.size()-1;
                    layoutManager.scrollToPosition(Position);
                    if(UserPage.id.equals("")){
                        new chatAsync(chat.this).execute(msg.getText().toString(),"admin",dateTime,receiver.getText().toString(),"SEND");
                        msg.setText("");
                    }
                    else{
                        new chatAsync(chat.this).execute(msg.getText().toString(),UserPage.id,dateTime,UserPage.id,"SEND");
                        msg.setText("");
                    }
                    Toast.makeText(chat.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private Handler handler = new Handler();
    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            Position = layoutManager.findFirstVisibleItemPosition();
            messageUpdate(receiver.getText().toString());
            handler.postDelayed(this, 5000);
        }
    };

    public void chatUpdate(ArrayList<String> messages,ArrayList<String> sender, ArrayList<String> time){
            for (int i = this.messages.size();i<messages.size();i++){
                this.messages.add(messages.get(i));
                this.sender.add(sender.get(i));
                this.time.add(time.get(i));

                adapter.notifyDataSetChanged();

        }
            if(c==1){
                layoutManager.scrollToPosition(messages.size()-1);
                c=2;
            }
            else{
                layoutManager.scrollToPosition(Position+1);
            }

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(myRunnable);
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                latLng = (LatLng) data.getParcelableExtra("picked_point");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Latitude", latLng.latitude);
                    jsonObject.put("Longitude", latLng.longitude);
                    if(UserPage.id.equals("")){
                        new chatAsync(chat.this).execute(jsonObject.toString(),"admin",dateTime,receiver.getText().toString(),"SEND");
                    }
                    else{
                        new chatAsync(chat.this).execute(jsonObject.toString(),UserPage.id,dateTime,UserPage.id,"SEND");
                    }
                    Toast.makeText(this, "Location sent successfully", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == 15 && resultCode == RESULT_OK) {
            // Get the captured image and display it
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    int bitmapSizeKB = byteArrayOutputStream.size() / 1024;
                    byteArrayOutputStream.close();
                    if (bitmapSizeKB<=490) {

                        new chatAsync(chat.this).execute("Image sent. Click to view Image.",UserPage.id,dateTime,UserPage.id,"SEND");
                        new chatSendImage().execute(new MyTaskParams(imageBitmap,100,UserPage.id,dateTime));
                    }
                    else if(bitmapSizeKB<=2000) {
                        new chatAsync(chat.this).execute("Image sent. Click to view Image.",UserPage.id,dateTime,UserPage.id,"SEND");
                        new chatSendImage().execute(new MyTaskParams(imageBitmap,75,UserPage.id,dateTime));
                    }
                    else if(bitmapSizeKB<=4000){
                        new chatAsync(chat.this).execute("Image sent. Click to view Image.",UserPage.id,dateTime,UserPage.id,"SEND");
                        new chatSendImage().execute(new MyTaskParams(imageBitmap,50,UserPage.id,dateTime));
                    }
                    else{
                        Toast.makeText(this, "Image is too big. Please take another image", Toast.LENGTH_SHORT).show();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }
    private void back(){
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        Intent intent;
        if(id.equals("admin")){
            intent = new Intent(chat.this, AdminChat.class);
        }
        else {
            intent = new Intent(chat.this, UserPage.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        startActivity(intent);
        finish();

    }
public void setName(String name){
        receiver.setVisibility(View.GONE);
        receiver2.setText(name);
        receiver2.setVisibility(View.VISIBLE);
    }
public void setImage(String bit){
    if(bit.equals("")){
        Bitmap bitmap = BitmapFactory.decodeResource(chat.this.getResources(), R.drawable.bulsu);
        pic.setImageBitmap(bitmap);
    }
    else {
        byte[] bytes = Base64.decode(bit, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        pic.setImageBitmap(bmp);
    }
    pic.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Drawable drawable = pic.getDrawable();
            Bitmap bm = drawableToBitmap(drawable);
            if(bm.getHeight()<bm.getWidth()){
                bitmap= rotateBitmap(bm, degreesToRotate);
            }
            else{
                bitmap=bm;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(chat.this);
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
        }});
    start();
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
    private void openCamera(){
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, 15);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 69) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission is granted, open the camera
                openCamera();
            } else {
                // Permission denied, handle the situation (e.g., show a message or request permission again)
            }
        }
    }
public void viewImage(String time){
            rel.setVisibility(View.VISIBLE);
            cam.setEnabled(false);
            loc.setEnabled(false);
            send.setEnabled(false);
    if(UserPage.id.equals("")){
        new chatGetImage(chat.this).execute(receiver.getText().toString(),time);
    }
    else{
        new chatGetImage(chat.this).execute(UserPage.id,time);
    }
}
public void show(Bitmap bmp){

    if(bmp.getHeight()<bmp.getWidth()){
        bitmap= rotateBitmap(bmp, degreesToRotate);
    }

    else{
        bitmap=bmp;
    }
    AlertDialog.Builder builder = new AlertDialog.Builder(chat.this);
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
}