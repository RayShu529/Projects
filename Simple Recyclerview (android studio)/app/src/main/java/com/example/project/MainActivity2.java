package com.example.project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
Button back2log, register;
EditText regfname, regmname, reglname, reguser, regpass, regemail, reghno, regst, regbr, regcity,
        regprov, regcontact, etspin1, etspin2, etspin3;
RadioButton regmale, regfemale;
CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10;
Spinner regspin1, regspin2, regspin3;
ImageView imageview;
final int CAM_CODE=1;
Bitmap bitmap;
Context c=this;
String gender, hobbies, name, user, pass, email, address, contact, secanswer1, secanswer2, secanswer3, encrypt;
Drawable oldimage;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initialize();
        listener();
    }
    private void initialize() {
        back2log = findViewById(R.id.back2log);
        register = findViewById(R.id.register);
        regfname = findViewById(R.id.regfname);
        regmname = findViewById(R.id.regmname);
        reglname = findViewById(R.id.reglname);
        reguser = findViewById(R.id.reguser);
        regpass = findViewById(R.id.regpass);
        regemail = findViewById(R.id.regemail);
        reghno = findViewById(R.id.reghno);
        regst = findViewById(R.id.regst);
        regbr = findViewById(R.id.regbr);
        regcity = findViewById(R.id.regcity);
        regprov = findViewById(R.id.regprov);
        regcontact = findViewById(R.id.regcontact);
        etspin1 = findViewById(R.id.etspin1);
        etspin2 = findViewById(R.id.etspin2);
        etspin3 = findViewById(R.id.etspin3);
        regmale = findViewById(R.id.regmale);
        regfemale = findViewById(R.id.regfemale);
        cb1 = findViewById(R.id.cb1); cb6 = findViewById(R.id.cb6);
        cb2 = findViewById(R.id.cb2); cb7 = findViewById(R.id.cb7);
        cb3 = findViewById(R.id.cb3); cb8 = findViewById(R.id.cb8);
        cb4 = findViewById(R.id.cb4); cb9 = findViewById(R.id.cb9);
        cb5 = findViewById(R.id.cb5); cb10 = findViewById(R.id.cb10);
        regspin1 = findViewById(R.id.regspin1);
        regspin2 = findViewById(R.id.regspin2);
        regspin3 = findViewById(R.id.regspin3);
        imageview = findViewById(R.id.imageview);
        String securityq [] = {"What is your Pet name?", "Mother's maiden Name?",
                "What is your favorite brand?", "What is your favorite dessert?",
                "Your bestfriend name?"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, securityq);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        regspin1.setAdapter(adapter);
        regspin2.setAdapter(adapter);
        regspin3.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAM_CODE)
        {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageview.setImageBitmap(bitmap);
        }
    }

    private void listener() {
        oldimage = imageview.getDrawable();
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,CAM_CODE);
            }
        });
        back2log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(c, MainActivity.class);
                startActivity(back);
                finish();
            }
        });
        regmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "male";
            }
        });
        regfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "female";
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hobbies="";
                if (cb1.isChecked()){

                    hobbies += cb1.getText().toString()+", ";
                }
                if (cb2.isChecked()){
                    hobbies += cb2.getText().toString()+", ";
                }
                if (cb3.isChecked()){
                    hobbies += cb3.getText().toString()+", ";
                }
                if (cb4.isChecked()){
                    hobbies += cb4.getText().toString()+", ";
                }
                if (cb5.isChecked()){
                    hobbies += cb5.getText().toString()+", ";
                }
                if (cb6.isChecked()){
                    hobbies += cb6.getText().toString()+", ";
                }
                if (cb7.isChecked()){
                    hobbies += cb7.getText().toString()+", ";
                }
                if (cb8.isChecked()){
                    hobbies += cb8.getText().toString()+", ";
                }
                if (cb9.isChecked()){
                    hobbies += cb9.getText().toString()+", ";
                }
                if (cb10.isChecked()){
                    hobbies += cb10.getText().toString()+", ";
                }
                if(imageview.getDrawable()==oldimage||regfname.getText().toString()==""||regmname.getText().toString()==""||reglname.getText().toString()==""||
                reguser.getText().toString()==""||regpass.getText().toString()==""||regemail.getText().toString()==""||gender==null||reghno.getText().toString()==""||
                regst.getText().toString()==""||regbr.getText().toString()==""||regcity.getText().toString()==""||regprov.getText().toString()==""||
                regcontact.getText().toString()==""||etspin1.getText().toString()==""||etspin2.getText().toString()==""||etspin3.getText().toString()==""||
                hobbies==""||regspin1.getSelectedItem()==regspin2.getSelectedItem()||regspin1.getSelectedItem()==regspin3.getSelectedItem()||regspin3.getSelectedItem()==regspin2.getSelectedItem()
                )
                {
                    if(imageview.getDrawable()==oldimage)
                    {
                        Toast.makeText(c, "Insert new Image", Toast.LENGTH_SHORT).show();
                    }
                    else if(regspin1.getSelectedItem()==regspin2.getSelectedItem()||regspin1.getSelectedItem()==regspin3.getSelectedItem()||regspin3.getSelectedItem()==regspin2.getSelectedItem())
                    {
                        Toast.makeText(c, "Choose another security question", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(c, "All fields are required", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    name=regfname.getText().toString()+" "+regmname.getText().toString() +" "+reglname.getText().toString();
                    user = reguser.getText().toString();
                    pass= regpass.getText().toString();
                    email = regemail.getText().toString();
                    address = reghno.getText().toString() +" "+regst.getText().toString()
                            +" "+regbr.getText().toString()+", "+regcity.getText().toString()+", "+regprov.getText().toString();
                    contact = regcontact.getText().toString();
                    secanswer1 = etspin1.getText().toString();
                    secanswer2= etspin2.getText().toString();
                    secanswer3= etspin3.getText().toString();
                    encrypt="";
                    for(int i = 1;i<=pass.length();i++)
                    {
                        encrypt +="*";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(c);
                    builder.setTitle("User Information")
                            .setMessage("Name: "+name+"\nUsername: "+user+"\nPassword: "+encrypt+"\nEmail: "+email+"\nGender: "
                            +gender+"\nAddress: "+address+"\nHobbies: "+hobbies+"\n"+regspin1.getSelectedItem()+":\n"+secanswer1
                                    +"\n"+regspin2.getSelectedItem()+":\n"+secanswer2+"\n"+regspin3.getSelectedItem()+":\n"+secanswer3)
                            .setCancelable(true)
                            .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent send = new Intent(c, MainActivity.class);
                                    send.putExtra("image", bitmap);
                                    send.putExtra("name", name);
                                    send.putExtra("user", user);
                                    send.putExtra("pass", pass);
                                    send.putExtra("email", email);
                                    send.putExtra("gender", gender);
                                    send.putExtra("address", address);
                                    send.putExtra("contact", contact);
                                    send.putExtra("hobbies", hobbies);
                                    send.putExtra("secanswer1", secanswer1);
                                    send.putExtra("secq1",regspin1.getSelectedItem().toString());
                                    send.putExtra("secanswer2", secanswer2);
                                    send.putExtra("secq2",regspin2.getSelectedItem().toString());
                                    send.putExtra("secanswer3", secanswer3);
                                    send.putExtra("secq3",regspin3.getSelectedItem().toString());
                                    startActivity(send);
                                    finish();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

    }
}