package com.example.capstone.manageFirstAids;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.capstone.R;
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
import java.util.ArrayList;

public class firstAidAsync extends AsyncTask<String,Void,String> {
    private manageFaid faid;
    public firstAidAsync( manageFaid faid) {
        this.faid = faid;
    }
    @Override
    protected String doInBackground(String... strings) {
        String action = strings[0];
        String injury = strings[1];
        String pass = strings[2];
        String data = strings[3];
        String adminID = strings[4];
        try {
            URL url = new URL("https://bulsuinfoapp.website/firstAid.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "action=" + URLEncoder.encode(action, "UTF-8") +"&injury=" + URLEncoder.encode(injury, "UTF-8") +"&pass=" + URLEncoder.encode(pass, "UTF-8") + "&data=" + URLEncoder.encode(data, "UTF-8")
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
            faid.rel.setVisibility(View.GONE);faid.showdel.setEnabled(true);faid.showadd.setEnabled(true);
            faid.addB.setEnabled(true);faid.addN.setEnabled(true);faid.addPIC.setEnabled(true);faid.addP.setEnabled(true);
            faid.delB.setEnabled(true);faid.delN.setEnabled(true);faid.delP.setEnabled(true);
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.has("result")){
                ArrayList<String> list = new ArrayList<>();
                JSONArray responseArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject item = responseArray.getJSONObject(i);
                    list.add(item.getString("injury"));
                }
                faid.getList(list);
            }
            else{

                String response = jsonObject.getString("response");
                if(response.equals("Incorrect Password")){
                    Toast.makeText(faid, response, Toast.LENGTH_SHORT).show();
                }
                else{
                    faid.sync();
                    faid.addP.setText("");faid.addN.setText("");
                    faid.addPIC.setImageDrawable(null);faid.addPIC.setImageBitmap(null);
                    Drawable drawable = ContextCompat.getDrawable(faid, R.drawable.addnew);
                    faid.addPIC.setBackground(drawable);
                    faid.delP.setText("");
                    Toast.makeText(faid, response, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
