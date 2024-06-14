package com.example.capstone.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstone.login.MainActivity;
import com.example.capstone.user.UserPage;
import com.google.firebase.messaging.FirebaseMessaging;

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

public class AdminCheck extends AsyncTask<String, Void, String> {
    private AdminPage a;
    public AdminCheck(AdminPage a) {
        this.a = a;
    }
    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/RoleCheck.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String postData = "id=" + URLEncoder.encode(id, "UTF-8");

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
        a.bldg.setEnabled(true);
        a.send.setEnabled(true);
        a.setting.setEnabled(true);
        a.manage.setEnabled(true);
        a.addAdmin.setEnabled(true);
        a.logout.setEnabled(true);
        a.rel.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            if(response.equalsIgnoreCase("superadmin")){
                a.addAdmin.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
