package com.example.capstone.IncidentReport;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.BulsuPlan.BulsuEvacPlan;
import com.example.capstone.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class AdminReportsView extends AppCompatActivity {
String caseNum;
ImageView img,back;
TextView sender,emergency, location,situation, date,caseNumber;
RelativeLayout rel;
Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reports_view);
        if(getIntent().hasExtra("caseNum")){
            caseNum = getIntent().getStringExtra("caseNum");
        }
        findID();
    }

    private void findID() {
        caseNumber = findViewById(R.id.caseNumber);
        caseNumber.setText(caseNum);
        back = findViewById(R.id.back);
        img = findViewById(R.id.img);
        sender = findViewById(R.id.sender);
        emergency = findViewById(R.id.emergency);
        location = findViewById(R.id.location);
        situation = findViewById(R.id.situation);
        date = findViewById(R.id.date);
        rel = findViewById(R.id.rel);
        syncData();
    }
public void setImage(String bit){
    byte[] bytes = Base64.decode(bit, Base64.DEFAULT);
    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    img.setImageBitmap(bmp);
    if(bmp.getHeight()<bmp.getWidth()){
        bitmap= rotateBitmap(bmp, 90.0f);
    }
    else{
        bitmap=bmp;
    }
    new AdminReportsViewAsync(AdminReportsView.this).execute("details",caseNum);
}
public void setDetails(String senderID,String emergencyType,String location,String situation,String dateTime){
        sender.setText(senderID);
        emergency.setText(emergencyType);
        this.location.setText(location);
        this.situation.setText(situation);
        date.setText(dateTime);
        rel.setVisibility(View.GONE);
        listeners2();
}
    private void syncData() {
        rel.setVisibility(View.VISIBLE);
        new AdminReportsViewAsync(AdminReportsView.this).execute("image",caseNum);
        listeners();
    }
    private void listeners2() {
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObject = new JSONObject(location.getText().toString());
                    double latitudeValue = jsonObject.getDouble("Latitude");
                    double longitudeValue = jsonObject.getDouble("Longitude");
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitudeValue + "," + longitudeValue);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name of Google Maps
                    if (mapIntent.resolveActivity(AdminReportsView.this.getPackageManager()) != null) {
                        // Start the Google Maps app with the directions
                        AdminReportsView.this.startActivityForResult(mapIntent,100);
                    }
                } catch (JSONException e) {
                    Toast.makeText(AdminReportsView.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminReportsView.this);
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
        });
    }
    private void listeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public Bitmap rotateBitmap(Bitmap source, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}