package com.example.capstone.user.evacPlan;

import android.os.AsyncTask;

import com.example.capstone.adminBuilding.edit.editBldg;

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

public class showEvacPlanAsync extends AsyncTask<String, Void, String> {
    private showEvacPlan act;
    public showEvacPlanAsync(showEvacPlan act) {
        this.act = act;
    }
    @Override
    protected String doInBackground(String... params) {
        String name = params[0];
        String floorNo = params[1];
        String result = "";

        try {

            URL serverUrl = new URL("https://bulsuinfoapp.website/userGetPic.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            String postData = "name=" + URLEncoder.encode(name, "UTF-8")+"&floorNo=" + URLEncoder.encode(floorNo, "UTF-8");
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
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result!=null){
            try {
                JSONObject jsonObject = new JSONObject(result);
                String response = jsonObject.getString("response");
                String num = jsonObject.getString("num");
                String flNum = jsonObject.getString("flNum");
                int x = Integer.parseInt(num);
                int y = Integer.parseInt(flNum);
                act.showPic(response,x,y);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}

