package com.example.capstone.login;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class forgotPassword extends AppCompatActivity {
EditText id1, code1,pass1,pass2;
TextView id2;
TextInputLayout t1,t2;
LinearLayout layoutcheck,layoutreset;
Button check, reset;
ImageView back;
RelativeLayout rel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        findID();
        // Set the icon for the action bar
        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();
        }
    }

    private void findID() {
        rel = findViewById(R.id.rel);
        id1 = findViewById(R.id.id1);
        code1= findViewById(R.id.code1);
        pass1 = findViewById(R.id.pass1);
        pass2 = findViewById(R.id.pass2);
        id2 = findViewById(R.id.id2);
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t1.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        t2.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        layoutcheck = findViewById(R.id.layoutcheck);
        layoutreset = findViewById(R.id.layoutreset);
        check = findViewById(R.id.check);
        reset = findViewById(R.id.reset);
        back = findViewById(R.id.back);
        listeners();
    }

    private void listeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = id1.getText().toString().trim();
                String code = code1.getText().toString().trim();
                if(id.equals("")){
                    id1.setError("Required");
                }
                else if(id.length()!=10){
                    id1.setError("Must be exactly 10 characters");
                }
                if(code.equals("")){
                    code1.setError("Required");
                }
                else if(code.length()!=6){
                    code1.setError("Must be exactly 6 characters");
                }
                else{
                    rel.setVisibility(View.VISIBLE);
                    new forgotPasswordAsync(forgotPassword.this, new forgotPasswordAsync.resultListener() {
                        @Override
                        public void onResult(String s) {
                            rel.setVisibility(View.GONE);
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String response = jsonObject.getString("response");
                                if(response.equalsIgnoreCase("Found")){
                                    layoutcheck.setVisibility(View.GONE);
                                    layoutreset.setVisibility(View.VISIBLE);
                                    id2.setText(id1.getText().toString().trim());
                                }
                                else{
                                    Toast.makeText(forgotPassword.this, "User/Code not found.Please check ID and code if correct", Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).execute("check",id,code);
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = id2.getText().toString().trim();
                String password1 = pass1.getText().toString().trim();
                String password2 = pass2.getText().toString().trim();
                if(id.equals("")){
                    layoutcheck.setVisibility(View.VISIBLE);
                    layoutreset.setVisibility(View.GONE);
                }
                else if(password1.equals("")){
                    pass1.setError("Required");

                }
                else if(password1.length()<6){
                    pass1.setError("Password must be at least 6 characters");
                }
                else if(!isInputValid(password1)){
                    pass1.setError("Password must have at least 1 UPPERCASE, 1 lowercase, 1 special character and 1 number");
                }
                else if(password2.equals("")){
                    pass2.setError("Required");
                }
                else if(!password1.equals(password2)){
                    pass2.setError("Incorrect Password.New Password and Confirm Password must be the same");
                }
                else{
                    rel.setVisibility(View.VISIBLE);
                    new forgotPasswordAsync(forgotPassword.this, new forgotPasswordAsync.resultListener() {
                        @Override
                        public void onResult(String s) {
                            rel.setVisibility(View.GONE);
                            Toast.makeText(forgotPassword.this, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).execute("reset",id,password1);
                }
            }
        });
    }
    private boolean isInputValid(String text) {
        // Define regular expressions for each criteria
        String uppercaseRegex = ".*[A-Z].*";
        String lowercaseRegex = ".*[a-z].*";
        String numberRegex = ".*\\d.*";
        String specialCharacterRegex = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*";

        // Check if the input matches all criteria
        return text.matches(uppercaseRegex) &&
                text.matches(lowercaseRegex) &&
                text.matches(numberRegex) &&
                text.matches(specialCharacterRegex);
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}