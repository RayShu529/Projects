package com.example.capstone.adminBuilding.edit;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.capstone.adminBuilding.add.Buildings;

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

public class clearPic extends AsyncTask<String, Void, String> {
    private editBldg bldg;
    public clearPic(editBldg bldg) {
        this.bldg = bldg;
    }
    @Override
    protected String doInBackground(String... params) {
        String name = params[0];
        String result = "";
        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/clear.php");
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
        }
        return result;
    }
    @Override
    protected void onPostExecute(String result) {
        try {
            bldg.edit.setEnabled(true);
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            if(response.equalsIgnoreCase("Success")){
                Toast.makeText(bldg,"Building Updated Successfully", Toast.LENGTH_SHORT).show();
            }
            else{

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
