package com.example.capstone.adminBuilding.restore;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.manageFirstAids.manageFaid;

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

public class restoreAsync extends AsyncTask<String,Void,String> {
    private restoreBuilding restore;
    public restoreAsync(restoreBuilding restore) {
        this.restore = restore;
    }
    @Override
    protected String doInBackground(String... strings) {
        String action = strings[0];
        String name = strings[1];
        String pass = strings[2];
        String id = strings[3];
        try {
            URL url = new URL("https://bulsuinfoapp.website/restoreBuilding.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "action=" + URLEncoder.encode(action, "UTF-8") +"&name=" + URLEncoder.encode(name, "UTF-8") +"&pass=" + URLEncoder.encode(pass, "UTF-8")
                    +"&id=" + URLEncoder.encode(id, "UTF-8");
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
        try {
            restore.rel.setVisibility(View.GONE);
            restore.restorebutton.setEnabled(true);restore.restorebldg.setEnabled(true);
            restore.restorepass.setEnabled(true);
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.has("result")){
                ArrayList<String> list = new ArrayList<>();
                JSONArray responseArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject item = responseArray.getJSONObject(i);
                    list.add(item.getString("name"));
                }
                restore.getList(list);
            }
            else{
                String response = jsonObject.getString("response");
                if(response.equals("Incorrect Password")){
                    Toast.makeText(restore, response, Toast.LENGTH_SHORT).show();
                }
                else{

                    restore.sync();
                    Toast.makeText(restore, response, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
