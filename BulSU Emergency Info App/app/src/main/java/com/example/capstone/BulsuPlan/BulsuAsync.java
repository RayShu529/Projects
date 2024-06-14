package com.example.capstone.BulsuPlan;

import android.os.AsyncTask;


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

public class    BulsuAsync extends AsyncTask<String,Void,String> {
    private BulsuEvacPlan plan;
    public BulsuAsync( BulsuEvacPlan plan) {
        this.plan = plan;
    }
    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String pass = strings[1];
        String data = strings[2];
        try {
            URL url = new URL("https://bulsuinfoapp.website/updateAdmin.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "type=" + URLEncoder.encode(type, "UTF-8") +"&pass=" + URLEncoder.encode(pass, "UTF-8") + "&data=" + URLEncoder.encode(data, "UTF-8");
            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(postData);
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            is.close();

            return response.toString();
        }catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
                if (jsonObject.has("type")) {
                    plan.show(response);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

