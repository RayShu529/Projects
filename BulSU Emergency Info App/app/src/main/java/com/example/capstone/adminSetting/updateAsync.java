package com.example.capstone.adminSetting;


import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.view.View;
import android.widget.Toast;

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

public class updateAsync extends AsyncTask<String,Void,String> {
    private adminSett bldg;
    public updateAsync( adminSett bldg) {
        this.bldg = bldg;
    }
    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String pass = strings[1];
        String data = strings[2];
        String adminID = strings[3];
        try {
            URL url = new URL("https://bulsuinfoapp.website/updateAdmin.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "type=" + URLEncoder.encode(type, "UTF-8") +"&pass=" + URLEncoder.encode(pass, "UTF-8") + "&data=" + URLEncoder.encode(data, "UTF-8")
                    + "&adminID=" + URLEncoder.encode(adminID, "UTF-8");
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
            bldg.rel.setVisibility(View.GONE);
            bldg.updateID.setEnabled(true);bldg.adminID.setEnabled(true);bldg.passID.setEnabled(true);bldg.passID2.setEnabled(true);
            bldg.updatePASS.setEnabled(true);bldg.adminPASS.setEnabled(true);bldg.passPASS.setEnabled(true);bldg.passPASS2.setEnabled(true);
            bldg.updatePIC.setEnabled(true);bldg.passPIC.setEnabled(true);bldg.passPIC2.setEnabled(true);bldg.adminPIC.setEnabled(true);
            bldg.showPIC.setEnabled(true);
            bldg.showID.setEnabled(true);
            bldg.showPASS.setEnabled(true);
            bldg.showCode.setEnabled(true);
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            if(response.equals("Incorrect Password")||response.equals("ID is already in the database")||response.equals("New Password must be different from the current Password")){
                Toast.makeText(bldg, response, Toast.LENGTH_SHORT).show();
            }
            else{
                if (jsonObject.has("type")) {
                    bldg.read(response);
                }
                else {
                    if(response.equals("ID updated Successfully")){
                        bldg.setID();
                    }

                    Toast.makeText(bldg, response, Toast.LENGTH_SHORT).show();
                }
                bldg.passID.setText("");bldg.passID2.setText("");
                bldg.adminPASS.setText("");bldg.passPASS.setText("");bldg.passPASS2.setText("");
                bldg.passPIC.setText("");bldg.passPIC2.setText("");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
