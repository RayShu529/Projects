package com.example.capstone.IncidentReport;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

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


public class UserReportAsync extends AsyncTask<String,Void,String> {
    private UserReport ur;
    public UserReportAsync(UserReport ur){
        this.ur = ur;
    }
    @Override
    protected String doInBackground(String... params) {
        String result = null;
        String caseNum= params[0];
        String action = params[1];
        String str2= params[2];
        String str3= params[3];
        String str4= params[4];
        String sender= params[5];
        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/Reports.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            String postData = "caseNum=" + URLEncoder.encode(caseNum, "UTF-8")
                    +"&action=" + URLEncoder.encode(action, "UTF-8")+
                    "&str2=" + URLEncoder.encode(str2, "UTF-8")+
                    "&str3=" + URLEncoder.encode(str3, "UTF-8")+
                    "&str4=" + URLEncoder.encode(str4, "UTF-8")+
                    "&sender=" + URLEncoder.encode(sender, "UTF-8");

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
        try {
                JSONObject jsonObject = new JSONObject(s);
                String response = jsonObject.getString("response");
                if(response.equals("success")){
                    ur.sendDetails();
                }
                else if(response.equals("success2")){
                    ur.rel.setVisibility(View.GONE);
                    Toast.makeText(ur, "Report sent", Toast.LENGTH_SHORT).show();
                    ur.finish();
                }
            } catch (JSONException e) {
            Toast.makeText(ur, "Server error. Please try again", Toast.LENGTH_SHORT).show();
            }

        }
    }

