package com.example.capstone.BulsuPlan;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

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

public class GetLocationAsync extends AsyncTask<String, Void, String> {
    private BulsuEvacPlan plan;
    public GetLocationAsync( BulsuEvacPlan plan) {
        this.plan = plan;
    }
    @Override
    protected String doInBackground(String... params) {
        String result="";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/LocationGet.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            OutputStream outputStream = urlConnection.getOutputStream();
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
            JSONObject jsonObject = new JSONObject(result);
            JSONArray responseArray = jsonObject.getJSONArray("response");
            if(responseArray.getString(0).equals("")){

            }
            else{
                plan.message = responseArray.getString(0);
                plan.lat = Double.parseDouble(responseArray.getString(1));
                plan.lon = Double.parseDouble(responseArray.getString(2));
                plan.up();
            }

        } catch (JSONException e) {

        }
    }
}
