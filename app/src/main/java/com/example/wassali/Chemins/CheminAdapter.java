package com.example.wassali.Chemins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wassali.R;

import java.util.ArrayList;

public class CheminAdapter extends RecyclerView.Adapter<CheminAdapter.ViewHolder> {

    private final RecycleViewInterface recycleViewInterface;
    Context context;
    ArrayList<CheminModel> cheminList;

    public CheminAdapter(RecycleViewInterface recycleViewInterface, Context context, ArrayList<CheminModel> cheminList) {
        this.recycleViewInterface = recycleViewInterface;
        this.context = context;
        this.cheminList = cheminList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);

        return new ViewHolder(view , recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CheminModel cheminModel = cheminList.get(position);

            holder.mes_depart.setText(cheminModel.adrDep);
            holder.mes_arrivee.setText(cheminModel.adrArr);


    }

    @Override
    public int getItemCount() {
        return cheminList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mes_depart,mes_arrivee;
        public ViewHolder(@NonNull View itemView , RecycleViewInterface recycleViewInterface) {
            super(itemView);


            mes_depart = itemView.findViewById(R.id.mes_depart);
            mes_arrivee = itemView.findViewById(R.id.mes_arrivee);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recycleViewInterface.onItemClick(pos);
                        }

                    }
                }
            });

        }
    }
}
