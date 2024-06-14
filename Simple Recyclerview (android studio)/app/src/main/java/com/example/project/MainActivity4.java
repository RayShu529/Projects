package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity4 extends AppCompatActivity {
Button submit, back;
EditText addentryname, addentryremark, addentryhobby;
RadioButton addentrymale, addentryfemale;
ImageView addentryimg;
TextView addnentrybday;
String name, remark, gender="", bday,hobbies;
Bitmap bitmap ;
final int CAM_CODE2=1;
DatePickerDialog date;
Context c = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    initialize();
    listener();
    }
    private void initialize() {
        back = findViewById(R.id.back);
        submit = findViewById(R.id.submit);
        addentryname = findViewById(R.id.addentryname);
        addentryimg = findViewById(R.id.addentryimg);
        addentryremark = findViewById(R.id.addentryremark);
        addnentrybday = findViewById(R.id.addentrybday);
        addentrymale = findViewById(R.id.addentrymale);
        addentryfemale = findViewById(R.id.addentryfemale);
        addentryhobby = findViewById(R.id.addentryhobby);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAM_CODE2)
        {
            bitmap = (Bitmap) data.getExtras().get("data");
            addentryimg.setImageBitmap(bitmap);
        }
    }

    private void listener() {
        bitmap=BitmapFactory.decodeResource(c.getResources(),R.drawable.addimgpic);
        addentryimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,CAM_CODE2);
            }
        });
        date = new DatePickerDialog(c, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                addnentrybday.setText(i+"/"+(i1+1)+"/"+i2);
            }
        }, 2000,11,10);
        addnentrybday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.show();
            }
        });
        addentrymale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {gender = "male";}
        });
        addentryfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {gender = "female";}
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(2,intent);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addentryname.getText().toString().equals("")||addentryremark.getText().toString().equals("")||gender.equals("")||addnentrybday.getText().toString().equals("")||addentryhobby.getText().toString().equals(""))
                {
                     if(addentryname.getText().toString().equals("")){
                        Toast.makeText(MainActivity4.this, "Input name", Toast.LENGTH_SHORT).show();
                    }
                    else if(addentryremark.getText().toString().equals("")){
                        Toast.makeText(MainActivity4.this, "Input remark", Toast.LENGTH_SHORT).show();
                    }
                     else if(addnentrybday.getText().toString().equals("")){
                         Toast.makeText(MainActivity4.this, "Input birthdate", Toast.LENGTH_SHORT).show();
                     }
                     else if(gender.equals("")){
                         Toast.makeText(MainActivity4.this, "Select gender", Toast.LENGTH_SHORT).show();
                     }
                     else if(addentryhobby.getText().toString().equals("")){
                         Toast.makeText(MainActivity4.this, "Input hobby", Toast.LENGTH_SHORT).show();
                     }
                }
                else
                {
                    name = addentryname.getText().toString();
                    remark = addentryremark.getText().toString();
                    bday = addnentrybday.getText().toString();
                    hobbies = addentryhobby.getText().toString();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bytearray = stream.toByteArray();
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("image",bytearray);
                    intent.putExtra("remark", remark);
                    intent.putExtra("bday", bday);
                    intent.putExtra("gender", gender);
                    intent.putExtra("hobby", hobbies);
                    setResult(2,intent);
                    finish();

                }

            }
        });

    }


}