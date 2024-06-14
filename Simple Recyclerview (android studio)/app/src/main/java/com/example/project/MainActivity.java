package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText loginuser, loginpass;
Button loginbutton, registerbutton;
Context c = this;
String gender, hobbies, name, user, pass, email, address, contact, secanswer1, secanswer2, secanswer3, secq1, secq2, secq3;
Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        listener();

    }
    private void initialize() {
        loginuser = findViewById(R.id.loginuser);
        loginpass = findViewById(R.id.loginpass);
        loginbutton = findViewById(R.id.loginbutton);
        registerbutton = findViewById(R.id.registerbutton);
    }
    private void listener() {
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().hasExtra("name"))
                {
                    Intent receive = getIntent();
                    bitmap = receive.getParcelableExtra("image");
                    name = receive.getExtras().getString("name");
                    user = receive.getExtras().getString("user");
                    pass = receive.getExtras().getString("pass");
                    email = receive.getExtras().getString("email");
                    gender = receive.getExtras().getString("gender");
                    address = receive.getExtras().getString("address");
                    contact = receive.getExtras().getString("contact");
                    hobbies = receive.getExtras().getString("hobbies");
                    secanswer1 = receive.getExtras().getString("secanswer1");
                    secq1 = receive.getExtras().getString("secq1");
                    secanswer2 = receive.getExtras().getString("secanswer2");
                    secq2 = receive.getExtras().getString("secq2");
                    secanswer3 = receive.getExtras().getString("secanswer3");
                    secq3 = receive.getExtras().getString("secq3");
                    if(loginuser.getText().toString().equalsIgnoreCase(user))
                    {
                        if(loginpass.getText().toString().equals(pass))
                        {
                            Intent send = new Intent(c, MainActivity3.class);
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
                            send.putExtra("secq1",secq1);
                            send.putExtra("secanswer2", secanswer2);
                            send.putExtra("secq2",secq2);
                            send.putExtra("secanswer3", secanswer3);
                            send.putExtra("secq3",secq3);
                            startActivity(send);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(c, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(c, "Invalid Username", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(c, "Account not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(c,MainActivity2.class);
                startActivity(i);
            }
        });
    }
}