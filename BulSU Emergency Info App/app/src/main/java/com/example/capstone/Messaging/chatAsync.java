package com.example.capstone.Messaging;


import android.os.AsyncTask;
import android.view.View;


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

public class chatAsync extends AsyncTask<String, Void, String> {
    private chat convo;
    public chatAsync(chat convo) {
        this.convo = convo;
    }
    @Override
    protected String doInBackground(String... params) {
        String message = params[0];
        String sender = params[1];
        String time = params[2];
        String user = params[3];
        String type = params[4];
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/chatAsync.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String postData = "message=" + URLEncoder.encode(message, "UTF-8") +
                    "&sender=" + URLEncoder.encode(sender, "UTF-8")+
                    "&time=" + URLEncoder.encode(time, "UTF-8")+
                    "&user=" + URLEncoder.encode(user, "UTF-8")+
                    "&type=" + URLEncoder.encode(type, "UTF-8");
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
            convo.send.setEnabled(true);convo.loc.setEnabled(true);convo.rel.setVisibility(View.GONE);
            ArrayList<String> messages = new ArrayList<>();
            ArrayList<String> sender = new ArrayList<>();
            ArrayList<String> time = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(result);
            JSONArray responseArray = jsonObject.getJSONArray("response");
            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject item = responseArray.getJSONObject(i);
                sender.add(item.getString("sender"));
                messages.add(item.getString("msg"));
                time.add(item.getString("time"));
            }
            convo.chatUpdate(messages,sender,time);

        } catch (JSONException e) {
            e.printStackTrace();

        }

    }
}

