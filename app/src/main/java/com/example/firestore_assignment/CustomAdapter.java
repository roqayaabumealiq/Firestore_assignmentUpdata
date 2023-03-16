package com.example.firestore_assignment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivity listActivity;
    List<Modle> modleList;
    Context context;

    public CustomAdapter(ListActivity listActivity,List<Modle> modleList){
        this.listActivity = listActivity;
        this.modleList = modleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model,parent,false);

        ViewHolder viewHolder = new ViewHolder(itemView);

        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String title = modleList.get(position).getTitle();
                String descr = modleList.get(position).getDescription();
                Toast.makeText(listActivity, title+"\n"+descr, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(listActivity);
                String[] options = {"Updata","Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0){
                            String id = modleList.get(position).getId();
                            String title = modleList.get(position).getTitle();
                            String description = modleList.get(position).getDescription();

                            Intent intent = new Intent(listActivity,MainActivity.class);
                            intent.putExtra("pId",id);
                            intent.putExtra("pTitle",title);
                            intent.putExtra("pDescription",description);

                            listActivity.startActivity(intent);

                        }
                        if (i == 1){

                        }
                    }
                }).create().show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTitleTv.setText(modleList.get(position).getTitle());
        holder.mDescriptionTv.setText(modleList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return modleList.size();
    }
}
