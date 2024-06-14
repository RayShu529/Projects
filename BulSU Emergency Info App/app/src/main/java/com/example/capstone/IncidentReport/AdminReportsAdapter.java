package com.example.capstone.IncidentReport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Messaging.chat;
import com.example.capstone.R;
import com.example.capstone.admin.AdminAdapter;
import com.example.capstone.admin.AdminChat;

import java.util.ArrayList;

public class AdminReportsAdapter extends RecyclerView.Adapter<AdminReportsAdapter.MyViewHolder>{
    private ArrayList<String> caseNum;
    private ArrayList<String> date;
    private ArrayList<String> emergency;
    public AdminReportsAdapter(ArrayList<String> caseNum, ArrayList<String> date, ArrayList<String> emergency) {
        this.caseNum = caseNum;
        this.date = date;
        this.emergency = emergency;

    }

    @Override
    public AdminReportsAdapter.MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adminreportsrecview, parent, false);

        return new AdminReportsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder( AdminReportsAdapter.MyViewHolder holder, int position) {
        holder.caseNum.setText(caseNum.get(position));
        holder.date.setText(date.get(position));
        holder.emergency.setText(emergency.get(position));
    }

    @Override
    public int getItemCount() {
        return caseNum.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView caseNum;
        public TextView date;
        public TextView emergency;
        public MyViewHolder(View itemView) {
            super(itemView);
            caseNum = itemView.findViewById(R.id.caseNum);
            date = itemView.findViewById(R.id.date);
            emergency = itemView.findViewById(R.id.emergency);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), AdminReportsView.class);
                    intent.putExtra("caseNum",caseNum.getText().toString());
                    ((Activity) itemView.getContext()).startActivity(intent);
                }
            });
        }
    }
}
