package com.example.capstone.user.evacPlan;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.adminSetting.adminSett;

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

public class GetBldgAsync extends AsyncTask<String,Void,String> {
    private userBldg bldg;
    String action = "";
    public GetBldgAsync( userBldg bldg) {
        this.bldg = bldg;
    }

    @Override
    protected String doInBackground(String... strings) {
        action = strings[0];
        String name = strings[1];

        try {
            URL url = new URL("https://bulsuinfoapp.website/userAsync.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "action=" + URLEncoder.encode(action, "UTF-8") +"&name=" + URLEncoder.encode(name, "UTF-8");
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
            if(result!=null){
                if(action.equals("READ")){
                    if(jsonObject.getString("response").equals("No Buildings to Display")){
                        bldg.tv.setText("No Buildings to Display");
                        bldg.rel.setVisibility(View.GONE);
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
                        bldg.show(bldgNames);
                        bldg.rel.setVisibility(View.GONE);
                    }
                }
                else if(action.equals("GetBuilding")){

                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
