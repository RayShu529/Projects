package com.example.capstone.adminNotif;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class LocationAsync extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
        String lat = params[0];
        String lon = params[1];
        String msg = params[2];
        String result="";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/LocationPost.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String postData = "lat=" + URLEncoder.encode(lat, "UTF-8") +
                    "&lon=" + URLEncoder.encode(lon, "UTF-8")+
                    "&msg=" + URLEncoder.encode(msg, "UTF-8");

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
}
