package com.example.capstone.admin;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.capstone.R;

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

public class ManageAdminAsync extends AsyncTask<String, Void, String> {
    private ManageAdmin manage;
    public ManageAdminAsync(ManageAdmin manage) {
        this.manage = manage;
    }
    @Override
    protected String doInBackground(String... params) {
        String nid = params[0];
        String npass= params[1];
        String id = params[2];
        String pass = params[3];
        String action = params[4];
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/ManageAdmin.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String postData = "nid=" + URLEncoder.encode(nid, "UTF-8")
                    +"&npass=" + URLEncoder.encode(npass, "UTF-8")
                    + "&id=" + URLEncoder.encode(id, "UTF-8")
                    +"&pass=" + URLEncoder.encode(pass, "UTF-8")
                    +"&action=" + URLEncoder.encode(action, "UTF-8");

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
            manage.showAdd.setEnabled(true);
            manage.showRemove.setEnabled(true);
            manage.addbutton.setEnabled(true);
            manage.addid.setEnabled(true);
            manage.addpass.setEnabled(true);
            manage.adminpass.setEnabled(true);
            manage.removebutton.setEnabled(true);
            manage.removespinner.setEnabled(true);
            manage.removepass.setEnabled(true);
            manage.rel.setVisibility(View.GONE);
            JSONObject jsonObject = new JSONObject(result);
            if(jsonObject.has("result")){
                ArrayList<String> list = new ArrayList<>();
                JSONArray responseArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject item = responseArray.getJSONObject(i);
                    list.add(item.getString("adminID"));
                }
                manage.getList(list);
            }
            else{
                String response = jsonObject.getString("response");
                if(response.equals("Incorrect Password")){
                    Toast.makeText(manage, response, Toast.LENGTH_SHORT).show();
                }
                else if(response.equals("ID is already in the database")){
                    Toast.makeText(manage, response, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(manage, response, Toast.LENGTH_SHORT).show();
                    manage.syncData();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
