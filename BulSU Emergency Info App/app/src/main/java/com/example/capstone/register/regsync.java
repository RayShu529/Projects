package com.example.capstone.register;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.login.MainActivity;

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

public class regsync extends AsyncTask<String, Void, String> {
    private static final String TAG = "InsertDataAsyncTask";
    private RegPage regPage;
    public regsync(RegPage regPage) {
        this.regPage = regPage;
    }

    @Override
    protected String doInBackground(String... params) {
        String id = params[0];
        String fname = params[1];
        String lname = params[2];
        String pass = params[3];
        String pic = params[4];
        String result = "";

        try {
            URL serverUrl = new URL("https://bulsuinfoapp.website/regpost.php");
            HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String postData = "id=" + URLEncoder.encode(id, "UTF-8") +"&fname=" + URLEncoder.encode(fname, "UTF-8") +
                    "&lname=" + URLEncoder.encode(lname, "UTF-8") +"&pass=" + URLEncoder.encode(pass, "UTF-8")
                    +"&pic=" + URLEncoder.encode(pic, "UTF-8");

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
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            if(response.equalsIgnoreCase("here")){
                regPage.checked();
            }
            else{
                regPage.reg.setEnabled(true);regPage.rel.setVisibility(View.GONE);
                regPage.id.setEnabled(true);regPage.pass.setEnabled(true);
                regPage.pass2.setEnabled(true);regPage.fname.setEnabled(true);
                regPage.lname.setEnabled(true);
                Toast.makeText(regPage, response, Toast.LENGTH_SHORT).show();
            }
            if(response.equalsIgnoreCase("User Registered Successfully")){
                Intent i = new Intent(this.regPage, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                regPage.startActivity(i);
                regPage.finish();
            }
        } catch (JSONException e) {
            regPage.reg.setEnabled(true);regPage.rel.setVisibility(View.GONE);
            regPage.id.setEnabled(true);regPage.pass.setEnabled(true);
            regPage.pass2.setEnabled(true);regPage.fname.setEnabled(true);
            regPage.lname.setEnabled(true);
            Toast.makeText(regPage, "Server error. Please try again", Toast.LENGTH_SHORT).show();
        }

    }
}
