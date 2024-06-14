package com.example.capstone.login;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class forgotPasswordAsync extends AsyncTask<String, Void, String> {
    private WeakReference<Context> contextRef;
    private resultListener listener;

    public forgotPasswordAsync(Context context, resultListener listener) {
        this.contextRef = new WeakReference<>(context);
        this.listener = listener;
    }
    @Override
    protected String doInBackground(String... params) {
        String action = params[0];
        String str1 = params[1];
        String str2 = params[2];
        try {
            URL url = new URL("https://bulsuinfoapp.website/forgotPassword.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "action=" + URLEncoder.encode(action, "UTF-8") +"&str1=" + URLEncoder.encode(str1, "UTF-8") + "&str2=" + URLEncoder.encode(str2, "UTF-8");

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
    protected void onPostExecute(String s) {
        Log.e("", "onPostExecute: "+s );
        if (s != null) {
            listener.onResult(s);
        }
    }

    public interface resultListener {
        void onResult(String s);
    }
}
