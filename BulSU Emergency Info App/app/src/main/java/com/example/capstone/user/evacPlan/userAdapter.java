package com.example.capstone.user.evacPlan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;
import com.example.capstone.adminBuilding.edit.editBldg;


public class userAdapter extends RecyclerView.Adapter<userAdapter.MyViewHolder> {

    private String[] itemTexts;

    public userAdapter(String[] itemTexts) {
        this.itemTexts = itemTexts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userrec, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(itemTexts[position]);
    }

    @Override
    public int getItemCount() {
        return itemTexts.length;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text_view);
            itemView.findViewById(R.id.item_button1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(itemView.getContext(), showEvacPlan.class);
                    i.putExtra("name", textView.getText().toString());
                    ((Activity) itemView.getContext()).startActivityForResult(i,1);
                }
            });
            itemView.findViewById(R.id.item_button2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String destination = textView.getText().toString()+",Bulacan State University, Malolos, Bulacan"; // Example address or building
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(destination));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    ((Activity) itemView.getContext()).startActivityForResult(mapIntent,1);

                }
            });
        }
    }
}



