package com.example.capstone.user.firstAid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.capstone.BulsuPlan.BulsuEvacPlan;

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

public class fAidAsync extends AsyncTask<String,Void,String> {
    private fAid f;
    public fAidAsync( fAid f) {
        this.f = f;
    }
    @Override
    protected String doInBackground(String... strings) {
        String action = strings[0];
        String get = strings[1];
        try {
            URL url = new URL("https://bulsuinfoapp.website/userFirstAid.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "action=" + URLEncoder.encode(action, "UTF-8")+"&get=" + URLEncoder.encode(get, "UTF-8");
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
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.has("result")){
                if(jsonObject.getString("result").equals("wala")){
                    f.tv.setVisibility(View.VISIBLE);
                    f.rel.setVisibility(View.GONE);
                }
                else{
                    ArrayList<String> list = new ArrayList<>();
                    JSONArray responseArray = jsonObject.getJSONArray("response");
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject item = responseArray.getJSONObject(i);
                        list.add(item.getString("injury"));
                    }
                    f.getList(list);
                }

            }
        else{
            f.spinner.setEnabled(true);
            String response = jsonObject.getString("response");
                f.getBit(response);
                f.rel.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
