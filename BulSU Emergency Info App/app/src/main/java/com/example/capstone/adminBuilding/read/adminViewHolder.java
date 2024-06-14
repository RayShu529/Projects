package com.example.capstone.adminBuilding.read;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;
import com.example.capstone.adminBuilding.edit.editBldg;

public class adminViewHolder extends RecyclerView.ViewHolder{
    private TextView textView;

    public adminViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.item_text_view);
        itemView.findViewById(R.id.item_button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(itemView.getContext(), editBldg.class);
                i.putExtra("name", textView.getText().toString());
                ((Activity) itemView.getContext()).startActivityForResult(i,1);
            }
        });
        itemView.findViewById(R.id.item_button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setMessage("Are you sure you want to archive "+textView.getText().toString()+"?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new archive((AdminBldg) itemView.getContext()).execute(textView.getText().toString());
                    }
                });
                builder.setCancelable(true);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void bind(String itemText) {
        textView.setText(itemText);
    }

}