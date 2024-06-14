package com.example.capstone.IncidentReport;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.admin.AdminChat;

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

public class AdminReportsAsync extends AsyncTask<String, Void, String> {
    private AdminReports adminReports;
    public AdminReportsAsync(AdminReports adminReports){
        this.adminReports = adminReports;
    }
    @Override
    protected String doInBackground(String... strings) {
        String action = strings[0];
        String result = null;
        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/Reports.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            String postData = "action=" + URLEncoder.encode(action, "UTF-8");

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
            if(jsonObject.getString("action").equals("read")){
                ArrayList<String> caseNum = new ArrayList<>();
                ArrayList<String> dateTime = new ArrayList<>();
                ArrayList<String> emergencyType = new ArrayList<>();
                JSONArray responseArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject item = responseArray.getJSONObject(i);
                    caseNum.add(item.getString("caseNum"));
                    dateTime.add(item.getString("dateTime").substring(0,10));
                    emergencyType.add(item.getString("emergencyType"));
                }
                adminReports.populate(caseNum,dateTime,emergencyType);
            }
            else if(jsonObject.getString("action").equals("readSpecific")){

            }

        } catch (JSONException e) {
            adminReports.async();
            Toast.makeText(adminReports, "Server error. Please wait", Toast.LENGTH_SHORT).show();
        }
    }
}
