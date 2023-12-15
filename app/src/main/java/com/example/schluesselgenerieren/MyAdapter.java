package com.example.schluesselgenerieren;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<MitarbeiterViewModel> list;

    public MyAdapter(Context context, ArrayList<MitarbeiterViewModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        MitarbeiterViewModel user = list.get(position);
        holder.mitarbeiternummer.setText(user.getMitarbeiternummer());
        holder.mitarbieterschluessel.setText(user.getMitarbieterschluessel());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mitarbeiternummer, mitarbieterschluessel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mitarbeiternummer = itemView.findViewById(R.id.mitarbeiterNummerView);
            mitarbieterschluessel = itemView.findViewById(R.id.schluesselView);

        }
    }
}
