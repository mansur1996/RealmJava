package com.example.realm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realm.R;
import com.example.realm.model.TaskDB;

import java.util.ArrayList;

import io.realm.RealmResults;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    ArrayList<TaskDB> notes = new ArrayList<>();

    public RecyclerViewAdapter(RealmResults<TaskDB> all) {
        notes.addAll(all);
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.roow, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TaskDB taskDB = notes.get(position);

        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).textViewRow.setText(taskDB.getTask());
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

//    @Override
//    public long getItemId(int position) {
//        return notes.get(0).getId();
//    }


    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewRow;
        MyViewHolder(View view){
            super(view);
            textViewRow = view.findViewById(R.id.txt_row_task);
        }
    }
}
