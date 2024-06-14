package com.example.capstone.admin;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AdminAsync extends AsyncTask<String, Void, String> {
    private AdminChat admin;
    public AdminAsync(AdminChat admin) {
        this.admin = admin;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/AdminAsync.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("GET");

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
            JSONArray responseArray = jsonObject.getJSONArray("response");

            if(responseArray.length()==0){
                admin.rel.setVisibility(View.GONE);
                admin.tv.setVisibility(View.VISIBLE);
                admin.tv.setText("No Messages");
            }
            else {
                String[] id = new String[responseArray.length()];
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject item = responseArray.getJSONObject(i);
                    id[i] = item.getString("sender");
                }
                admin.update(id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
