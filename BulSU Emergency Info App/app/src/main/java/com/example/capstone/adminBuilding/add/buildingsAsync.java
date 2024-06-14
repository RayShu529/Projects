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

public class buildingsAsync extends AsyncTask<String,Void,String> {
    private Buildings bldg;
    public buildingsAsync(Buildings bldg) {
        this.bldg = bldg;
    }
    @Override
    protected String doInBackground(String... params) {
        String name = params[0];
        String floor = params[1];
        String encodedData = params[2];
        try {
            URL url = new URL("https://bulsuinfoapp.website/imgpost.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "name=" + URLEncoder.encode(name, "UTF-8") +"&floorNo=" + URLEncoder.encode(floor, "UTF-8") + "&byteArray=" + URLEncoder.encode(encodedData, "UTF-8");
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
            if(response.equalsIgnoreCase("Building Saved Successfully")){
                bldg.finish();
            }
            else{
                bldg.addbldg.setEnabled(true);bldg.bldgname.setEnabled(true);
                bldg.rel.setVisibility(View.GONE);
                bldg.first.setEnabled(true);
                bldg.second.setEnabled(true);
                bldg.third.setEnabled(true);
                bldg.fourth.setEnabled(true);
                Toast.makeText(bldg,response, Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
