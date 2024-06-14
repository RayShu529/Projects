package com.example.capstone.adminBuilding.read;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

public class adminAdapter extends RecyclerView.Adapter<adminViewHolder> {

    private String[] itemTexts;

    public adminAdapter(String[] itemTexts) {
        this.itemTexts = itemTexts;
    }

    @Override
    public adminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adminrec, parent, false);

        return new adminViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(adminViewHolder holder, int position) {
        holder.bind(itemTexts[position]);

    }

    @Override
    public int getItemCount() {
        return itemTexts.length;
    }
}
