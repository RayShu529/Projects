package com.example.capstone.adminBuilding.read;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class readBldg extends AsyncTask<String, Void, String> {
    private AdminBldg admin;
    public readBldg(AdminBldg admin) {
        this.admin = admin;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/readBldg.php");
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
            admin.rel.setVisibility(View.GONE);
            JSONObject jsonObject = new JSONObject(result);
            if(result!=null){
                if(jsonObject.getString("response").equals("No Buildings to Display")){
                    admin.tv.setText("No Buildings to Display");
                }
                else{
                    String num = jsonObject.getString("num");
                    int x = Integer.parseInt(num);
                    String[] bldgNames = new String[x];
                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                    for(int y = 0;y<x;y++){
                        JSONObject response = jsonArray.getJSONObject(y);
                        bldgNames[y] = response.getString("name");
                    }
                    admin.update(bldgNames);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

