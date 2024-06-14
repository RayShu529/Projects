package com.example.capstone.admin;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Messaging.chat;
import com.example.capstone.R;
import com.example.capstone.manageFirstAids.firstAidAsync;
import com.example.capstone.manageFirstAids.manageFaid;
import com.example.capstone.user.UserPage;
import com.example.capstone.user.evacPlan.showEvacPlan;

import java.util.ArrayList;

public class AdminAdapter extends RecyclerView.Adapter<com.example.capstone.admin.AdminAdapter.MyViewHolder> {
    private ArrayList<String> itemTexts;
    private ArrayList<String> tvname;
    private ArrayList<Bitmap> tvpic;
    AdminChat admin;

    public AdminAdapter(ArrayList<String> itemTexts,ArrayList<String> tvname,ArrayList<Bitmap> tvpic,AdminChat admin) {
        this.itemTexts = itemTexts;
        this.tvname = tvname;
        this.tvpic = tvpic;
        this.admin = admin;
    }

    @Override
    public com.example.capstone.admin.AdminAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adminchat, parent, false);

        return new com.example.capstone.admin.AdminAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(com.example.capstone.admin.AdminAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(itemTexts.get(position));
        holder.tvname.setText(tvname.get(position));
        holder.tvpic.setImageBitmap(tvpic.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(admin);
                builder.setTitle("Archive Message")
                        .setMessage("The messages will be archived and not visible unless the student sends you a new message.\nAre you sure about that?")
                        .setCancelable(true)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int position = holder.getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    new AdminChatArchive().execute(itemTexts.get(position));
                                    itemTexts.remove(position);
                                    tvname.remove(position);
                                    tvpic.remove(position);
                                    // Notify the adapter that an item has been removed
                                    notifyItemRemoved(position);
                                }
                                if (itemTexts.size() == 0) {
                                    admin.show();
                                }

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Code to handle the negative button click
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemTexts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView tvname;
        public ImageView tvpic;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            tvname = itemView.findViewById(R.id.tvname);
            tvpic = itemView.findViewById(R.id.tvpic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), chat.class);
                    intent.putExtra("name",textView.getText().toString());
                    ((Activity) itemView.getContext()).startActivity(intent);
                }
            });

        }
    }
}




