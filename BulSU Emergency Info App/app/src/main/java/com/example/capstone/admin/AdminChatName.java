package com.example.capstone.admin;

import android.os.AsyncTask;

import com.example.capstone.Messaging.chat;

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

public class AdminChatName extends AsyncTask<String, Void, String> {
    private AdminChat a;
    public AdminChatName(AdminChat a) {
        this.a = a;
    }
    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/getChat.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String postData = "id=" + URLEncoder.encode(id, "UTF-8")+"&action=" + URLEncoder.encode("action", "UTF-8");
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
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray responseArray = jsonObject.getJSONArray("response");
            JSONObject item = responseArray.getJSONObject(0);
            String name = item.getString("userName");
            a.checkName(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
