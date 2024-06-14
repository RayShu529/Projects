package com.example.capstone.Messaging;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.BulsuPlan.BulsuEvacPlan;

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

public class chatGetImage extends AsyncTask<String, Void, String> {
    private chat c;
    public chatGetImage( chat c) {
        this.c = c;
    }
    @Override
    protected String doInBackground(String... strings) {
        String id = strings[0];
        String time = strings[1];
        try {
            URL url = new URL("https://bulsuinfoapp.website/chatGetImage.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "id=" + URLEncoder.encode(id, "UTF-8")
                    +"&time=" + URLEncoder.encode(time, "UTF-8");
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
        c.rel.setVisibility(View.GONE);
        c.cam.setEnabled(true);
        c.loc.setEnabled(true);
        c.send.setEnabled(true);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String response = jsonObject.getString("response");
                if(response.equals(null)||response.equals("")){
                    Toast.makeText(c, "Server Error. Please try again", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        Log.e("",response);
                        byte[] bytes = Base64.decode(response, Base64.DEFAULT);
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        c.show(bmp);
                    }catch (Exception e){
                        Toast.makeText(c, "Server Error. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {

            }
    }
}
