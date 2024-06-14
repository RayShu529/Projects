package com.example.capstone.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.user.UserPage;
import com.example.capstone.admin.AdminPage;
import com.google.firebase.messaging.FirebaseMessaging;

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

public class logsync extends AsyncTask<String, Void, String> {
    private static final String TAG = "InsertDataAsyncTask";
    private MainActivity mainActivity;
    public logsync(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String pass = params[1];
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/post.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String postData = "id=" + URLEncoder.encode(id, "UTF-8") +
                    "&pass=" + URLEncoder.encode(pass, "UTF-8");

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
            Log.e(TAG, "Error sending POST request: " + e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            mainActivity.log.setEnabled(true);mainActivity.pass.setEnabled(true);mainActivity.id.setEnabled(true);mainActivity.rel.setVisibility(View.GONE);
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            if(response.equalsIgnoreCase("Welcome Admin")){
                mainActivity.userSet("admin",mainActivity.sID);
                Toast.makeText(mainActivity, "Welcome Admin", Toast.LENGTH_SHORT).show();
                FirebaseMessaging.getInstance().subscribeToTopic("admin");
                Intent i = new Intent(this.mainActivity, AdminPage.class);
                mainActivity.startActivity(i);
            }
            else if(response.substring(0,12).equalsIgnoreCase("Welcome User")){
                mainActivity.userSet(mainActivity.sID,response.substring(13));
                Toast.makeText(mainActivity, "Welcome "+response.substring(13), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this.mainActivity, UserPage.class);
                mainActivity.startActivity(i);
            }
            else{
                Toast.makeText(mainActivity, response, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
