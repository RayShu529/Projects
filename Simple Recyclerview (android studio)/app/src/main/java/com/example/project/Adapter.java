package com.example.project;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<model> users;
    public Adapter(List<model>users){this.users=users;}
    OnItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Bitmap resource  = users.get(position).getImgview();
        String name = users.get(position).getTv();
        String remarks = users.get(position).getTv2();
        holder.setData(resource, name, remarks);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnEditClick(int position);
        void OnDeleteClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listenerActivity) {
        listener = listenerActivity;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgview=itemView.findViewById(R.id.imgview);
            tv = itemView.findViewById(R.id.tv);
            tv2 = itemView.findViewById(R.id.tv2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            itemView.findViewById(R.id.editbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnEditClick(position);
                        }
                    }
                }
            });
            itemView.findViewById(R.id.delbtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.OnDeleteClick(position);
                        }
                    }
                }
            });


        }
        private ImageView imgview;
        private TextView tv;
        private TextView tv2;

        public void setData(Bitmap resource, String name, String remarks) {
            imgview.setImageBitmap(resource);
            tv.setText(name);
            tv2.setText(remarks);
        }
    }
}
