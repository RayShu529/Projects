package com.example.capstone.Messaging;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

class MyTaskParams {
    Bitmap bitmap;
    int intValue;
    String id;
    String time;

    MyTaskParams(Bitmap bitmap, int intValue, String id,String time) {
        this.bitmap = bitmap;
        this.intValue = intValue;
        this.id = id;
        this.time = time;
    }
}

public class chatSendImage extends AsyncTask<MyTaskParams,Void,String> {
String result="";
    @Override
    protected String doInBackground(MyTaskParams... params) {
        Bitmap bitmap = params[0].bitmap;
        int intValue = params[0].intValue;
        String id = params[0].id;
        String time = params[0].time;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, intValue, stream);
        String pic = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT);
        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/chatSendImage.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            String postData = "pic=" + URLEncoder.encode(pic, "UTF-8")
                    +"&id=" + URLEncoder.encode(id, "UTF-8")+
                    "&time=" + URLEncoder.encode(time, "UTF-8");

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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
