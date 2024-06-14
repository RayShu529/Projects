package com.example.capstone.user.settings;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.adminSetting.adminSett;

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

public class settingsAsync extends AsyncTask<String,Void,String> {
    private settings setting;
    public settingsAsync(settings setting) {
        this.setting = setting;
    }
    @Override
    protected String doInBackground(String... strings) {
        String ID = strings[0];
        String OLD = strings[1];
        String NEW = strings[2];
        try {
            URL url = new URL("https://bulsuinfoapp.website/updateUser.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String postData = "ID=" + URLEncoder.encode(ID, "UTF-8") +"&OLD=" + URLEncoder.encode(OLD, "UTF-8") + "&NEW=" + URLEncoder.encode(NEW, "UTF-8");
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
        if(result!=null){
            try {
                setting.rel.setVisibility(View.GONE);
                setting.cp.setEnabled(true);
                setting.op.setEnabled(true);
                setting.np.setEnabled(true);
                setting.np2.setEnabled(true);
                setting.showPass.setEnabled(true);
                setting.showPic.setEnabled(true);
                JSONObject jsonObject = new JSONObject(result);
                String response = jsonObject.getString("response");
                if(response.equals("Incorrect Password")||response.equals("New Password must be different from the current Password")){
                    Toast.makeText(setting, response, Toast.LENGTH_SHORT).show();
                }
                else{
                    setting.op.setText("");setting.np.setText("");setting.np2.setText("");
                    Toast.makeText(setting, response, Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                Toast.makeText(setting, "Server error. Please try again", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            setting.rel.setVisibility(View.GONE);
            setting.cp.setEnabled(true);
            setting.op.setEnabled(true);
            setting.np.setEnabled(true);
            setting.np2.setEnabled(true);
            setting.showPass.setEnabled(true);
            setting.showPic.setEnabled(true);
            Toast.makeText(setting, "Server error. Please try again", Toast.LENGTH_SHORT).show();
        }

    }
}

