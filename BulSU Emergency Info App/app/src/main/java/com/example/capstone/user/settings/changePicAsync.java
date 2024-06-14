package com.example.capstone.user.settings;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.capstone.R;
import com.example.capstone.login.MainActivity;
import com.example.capstone.register.RegPage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class changePicAsync extends AsyncTask<String, Void, String> {
    private settings sett;
    public changePicAsync(settings sett) {
        this.sett = sett;
    }

    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String pass = params[1];
        String pic = params[2];
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/updatePic.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String postData = "id=" + URLEncoder.encode(id, "UTF-8") +"&pic=" + URLEncoder.encode(pic, "UTF-8")+"&pass=" + URLEncoder.encode(pass, "UTF-8");
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postData);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            inputStream.close();

            result = response.toString();

            urlConnection.disconnect();
        } catch (Exception e) {

        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        sett.rel.setVisibility(View.GONE);
        sett.showPass.setEnabled(true);
        sett.showPic.setEnabled(true);
        sett.pic.setEnabled(true);
        sett.passPic.setEnabled(true);
        sett.changePic.setEnabled(true);
    if(result!=""){
        try {
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            if(response.equals("Incorrect Password")){
                Toast.makeText(sett, response, Toast.LENGTH_SHORT).show();
            }
            else{
                sett.passPic.setText("");
                sett.pic.setImageBitmap(null);
                sett.pic.setImageDrawable(null);
                Drawable drawable = ContextCompat.getDrawable(sett, R.drawable.person);
                sett.pic.setBackground(drawable);
                Toast.makeText(sett, response, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            Toast.makeText(sett, "Server error. Please try again", Toast.LENGTH_SHORT).show();
        }
    }
    else{
        Toast.makeText(sett, "Server error. Please try again", Toast.LENGTH_SHORT).show();
    }
    }
}
