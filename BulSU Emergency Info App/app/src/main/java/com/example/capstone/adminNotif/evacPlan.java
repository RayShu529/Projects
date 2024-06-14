package com.example.capstone.adminNotif;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.adminSetting.adminSett;

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

public class evacPlan extends AsyncTask<String,Void,String> {
    private sendNotif bldg;
    public evacPlan( sendNotif bldg) {
        this.bldg = bldg;
    }
    @Override
    protected String doInBackground(String... strings) {
        String type = "READ";
        String pass = "1";
        String data = "1";
        try {
            URL url = new URL("https://bulsuinfoapp.website/updateAdmin.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "type=" + URLEncoder.encode(type, "UTF-8") +"&pass=" + URLEncoder.encode(pass, "UTF-8") + "&data=" + URLEncoder.encode(data, "UTF-8");
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
        bldg.rel.setVisibility(View.GONE);
        try {
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            bldg.read(response);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

