package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class MainActivity5 extends AppCompatActivity {
ImageView viewimg;
TextView viewname, viewremark, viewbday,viewgender,viewhobby;
Button back;
String name, remark, bday, gender, hobby;
Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initialize();
        listener();
    }

    private void initialize() {
        viewimg = findViewById(R.id.viewimg);
        viewname = findViewById(R.id.viewname);
        viewremark = findViewById(R.id.viewremark);
        viewbday = findViewById(R.id.viewbday);
        viewgender = findViewById(R.id.viewgender);
        viewhobby = findViewById(R.id.viewhobby);
        back = findViewById(R.id.back);
    }

    private void listener() {
        if(getIntent().hasExtra("name"))
        {
            name = getIntent().getStringExtra("name");
            remark = getIntent().getStringExtra("remark");
            bday = getIntent().getStringExtra("bday");
            gender = getIntent().getStringExtra("gender");
            hobby = getIntent().getStringExtra("hobby");
            byte[] bytearray = getIntent().getByteArrayExtra("image");
            bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
            viewimg.setImageBitmap(bitmap);
            viewname.setText("Name: "+name);
            viewremark.setText("Remark: "+remark);
            viewbday.setText("Birthday: "+bday);
            viewgender.setText("Gender: "+gender);
            viewhobby.setText("Hobby: "+hobby);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(10,intent);
                finish();
            }
        });
    }
}