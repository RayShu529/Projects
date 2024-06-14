package com.example.capstone.adminBuilding.add;

import android.os.AsyncTask;
import android.util.Log;
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

public class bldgCheck extends AsyncTask<String, Void, String> {
    private Buildings bldg;
    public bldgCheck(Buildings bldg) {
        this.bldg = bldg;
    }
    @Override
    protected String doInBackground(String... params) {
        String name = params[0];
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/bldgCheck.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            String postData = "name=" + URLEncoder.encode(name, "UTF-8");
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
            Log.e("TAG", "Error sending POST request: " + e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            if(response.equalsIgnoreCase("Available")){
                bldg.send();
            }
            else{
                bldg.addbldg.setEnabled(true);bldg.bldgname.setEnabled(true);
                bldg.rel.setVisibility(View.GONE);
                bldg.first.setEnabled(true);
                bldg.second.setEnabled(true);
                bldg.third.setEnabled(true);
                bldg.fourth.setEnabled(true);
                Toast.makeText(bldg, response, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

