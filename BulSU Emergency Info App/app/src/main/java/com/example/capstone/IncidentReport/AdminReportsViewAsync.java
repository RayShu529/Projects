package com.example.capstone.IncidentReport;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
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
import java.util.ArrayList;

public class AdminReportsViewAsync extends AsyncTask<String, Void, String> {
    private AdminReportsView admin;
    public AdminReportsViewAsync(AdminReportsView admin){
        this.admin = admin;
    }

    @Override
    protected String doInBackground(String... params) {
        String action = params[0];
        String caseNum = params[1];
        String result = null;
        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/SpecificReport.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            String postData = "action=" + URLEncoder.encode(action, "UTF-8")+
                    "&caseNum=" + URLEncoder.encode(caseNum, "UTF-8");

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
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        try{

            JSONObject jsonObject = new JSONObject(s);
            String action = jsonObject.getString("action");
            if (action.equals("image")) {
                String response = jsonObject.getString("response");
                admin.setImage(response);
            }
            else if (action.equals("details")) {
                JSONArray responseArray = jsonObject.getJSONArray("response");
                JSONObject item = responseArray.getJSONObject(0);
                String senderID = item.getString("senderID");
                String emergencyType = item.getString("emergencyType");
                String location = item.getString("location");
                String situation = item.getString("situation");
                String dateTime = item.getString("dateTime");
                admin.setDetails(senderID,emergencyType,location,situation,dateTime);
            }
        } catch (JSONException e) {
            Toast.makeText(admin, "Server error. Please wait", Toast.LENGTH_SHORT).show();
        }
    }
}
