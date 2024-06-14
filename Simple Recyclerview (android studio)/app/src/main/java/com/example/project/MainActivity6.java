package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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

public class MainActivity6 extends AppCompatActivity {
ImageView editimg;
Button back, save;
TextView editbday;
EditText editname, editremark, edithobby;
RadioButton editmale, editfemale;
DatePickerDialog date;
Context c = this;
int position;
    String name, remark, bday, gender, hobby;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        initialize();
        listener();
    }

    private void initialize() {
        editimg = findViewById(R.id.editimg);
        editname = findViewById(R.id.editname);
        editremark = findViewById(R.id.editremark);
        editbday = findViewById(R.id.editbday);
        edithobby = findViewById(R.id.edithobby);
        editmale = findViewById(R.id.editmale);
        editfemale = findViewById(R.id.editfemale);
        back = findViewById(R.id.back);
        save= findViewById(R.id.save);

        if (getIntent().hasExtra("name"))
        {
            name = getIntent().getStringExtra("name");
            remark = getIntent().getStringExtra("remark");
            bday = getIntent().getStringExtra("bday");
            gender = getIntent().getStringExtra("gender");
            hobby = getIntent().getStringExtra("hobby");
            byte[] bytearray = getIntent().getByteArrayExtra("image");
            bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
            position = getIntent().getIntExtra("position",0);
            editimg.setImageBitmap(bitmap);
            editname.setText(name);
            editremark.setText(remark);
            editbday.setText(bday);
            edithobby.setText(hobby);
            if(gender.equals("male"))
            {
                editmale.setChecked(true);
            }
            if(gender.equals("female"))
            {
                editmale.setChecked(true);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==15)
        {
            bitmap = (Bitmap) data.getExtras().get("data");
            editimg.setImageBitmap(bitmap);
        }
    }

    private void listener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(11,intent);
                finish();
            }
        });
        editmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender="male";
            }
        });
        editfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender ="female";
            }
        });
        date = new DatePickerDialog(c, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                editbday.setText(i+"/"+(i1+1)+"/"+i2);
            }
        }, 2000,11,10);
        editbday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date.show();
            }
        });
        editimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,15);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editname.getText().toString().equals("")||editremark.getText().toString().equals("")||gender.equals("")||editbday.getText().toString().equals("")||edithobby.getText().toString().equals(""))
                {
                    if(editname.getText().toString().equals("")){
                        Toast.makeText(c, "Input name", Toast.LENGTH_SHORT).show();
                    }
                    else if(editremark.getText().toString().equals("")){
                        Toast.makeText(c, "Input remark", Toast.LENGTH_SHORT).show();
                    }
                    else if(editbday.getText().toString().equals("")){
                        Toast.makeText(c, "Input birthdate", Toast.LENGTH_SHORT).show();
                    }
                    else if(gender.equals("")){
                        Toast.makeText(c, "Select gender", Toast.LENGTH_SHORT).show();
                    }
                    else if(edithobby.getText().toString().equals("")){
                        Toast.makeText(c, "Input hobby", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    name = editname.getText().toString();
                    remark = editremark.getText().toString();
                    bday = editbday.getText().toString();
                    hobby = edithobby.getText().toString();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] bytearray = stream.toByteArray();
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    intent.putExtra("image",bytearray);
                    intent.putExtra("remark", remark);
                    intent.putExtra("bday", bday);
                    intent.putExtra("gender", gender);
                    intent.putExtra("hobby", hobby);
                    intent.putExtra("position", position);
                    setResult(3,intent);
                    finish();

                }

            }
        });

    }
}