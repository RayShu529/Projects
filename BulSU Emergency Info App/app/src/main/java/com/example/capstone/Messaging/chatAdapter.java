package com.example.capstone.Messaging;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter<com.example.capstone.Messaging.chatAdapter.MyViewHolder> {
    private String receiver;
    private ArrayList<String> msg;
    private ArrayList<String> time;
    private ArrayList<String> sender;
     chat ch;
    public chatAdapter(ArrayList<String> msg,ArrayList<String> time,ArrayList<String> sender,String receiver,chat c) {
        this.msg = msg;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
        this.ch = c;
    }

    @Override
    public com.example.capstone.Messaging.chatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatview, parent, false);

        return new com.example.capstone.Messaging.chatAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.example.capstone.Messaging.chatAdapter.MyViewHolder holder, int position) {
       holder.message.setText(msg.get(position));
       holder.time.setText(time.get(position));
        if(receiver.equals("Admin")){
           if(sender.get(position).equals("admin")){
               holder.m.gravity = Gravity.LEFT;
               holder.message.setLayoutParams(holder.m);
               holder.t.gravity = Gravity.LEFT;
               holder.time.setLayoutParams(holder.m);
           }
           else{
               holder.m.gravity = Gravity.RIGHT;
               holder.message.setLayoutParams(holder.m);
               holder.t.gravity = Gravity.RIGHT;
               holder.time.setLayoutParams(holder.m);
           }
       }
       else{

           if(sender.get(position).equals("admin")){
               holder.m.gravity = Gravity.RIGHT;
               holder.message.setLayoutParams(holder.m);
               holder.t.gravity = Gravity.RIGHT;
               holder.time.setLayoutParams(holder.m);

           }
           else{
               holder.m.gravity = Gravity.LEFT;
               holder.message.setLayoutParams(holder.m);
               holder.t.gravity = Gravity.LEFT;
               holder.time.setLayoutParams(holder.m);

           }
       }
        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.message.getText().toString().equals("Image sent. Click to view Image.")){
                    ch.viewImage(holder.time.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return msg.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView message, time;
        public LinearLayout.LayoutParams m,t;
        public MyViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            m = (LinearLayout.LayoutParams) message.getLayoutParams();
            t = (LinearLayout.LayoutParams) time.getLayoutParams();
            message.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    try {
                        JSONObject jsonObject = new JSONObject(message.getText().toString());
                        double latitudeValue = jsonObject.getDouble("Latitude");
                        double longitudeValue = jsonObject.getDouble("Longitude");
                        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitudeValue + "," + longitudeValue);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps"); // Specify the package name of Google Maps
                        if (mapIntent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
                            // Start the Google Maps app with the directions
                            ((Activity) itemView.getContext()).startActivityForResult(mapIntent,100);

                        }
                    } catch (JSONException e) {
                        Toast.makeText(itemView.getContext(), "This message is not a valid location.", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
            });
        }
    }
}
