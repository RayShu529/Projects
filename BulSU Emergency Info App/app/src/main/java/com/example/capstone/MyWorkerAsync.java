package com.example.capstone;

import android.os.AsyncTask;

import com.example.capstone.Messaging.chat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MyWorkerAsync extends AsyncTask<String, Void, String> {
    private MyForegroundService myWorker;
    public MyWorkerAsync(MyForegroundService myWorker) {
        this.myWorker = myWorker;
    }
    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String result = "";
        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/WorkerAsync.php");
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
        try {
            ArrayList<String> messages = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            ArrayList<String> sender = new ArrayList<>();
            JSONArray responseArray = jsonObject.getJSONArray("response");
            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject item = responseArray.getJSONObject(i);
                messages.add(item.getString("msg"));
                sender.add(item.getString("sender"));
            }
            myWorker.updates(messages,sender);

        } catch (JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
