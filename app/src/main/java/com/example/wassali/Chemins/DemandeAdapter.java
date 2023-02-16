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

public class DemandeAdapter extends RecyclerView.Adapter<DemandeAdapter.ViewHolder>{

    private final RecycleViewInterface recycleViewInterface;

    Context context;
    ArrayList<DemandeModel> demandeList;

    public DemandeAdapter( RecycleViewInterface recycleViewInterface , Context context, ArrayList<DemandeModel> demandeList ) {
        this.context = context;
        this.demandeList = demandeList;
        this.recycleViewInterface = recycleViewInterface;
    }

    @NonNull
    @Override
    public DemandeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);

        return new DemandeAdapter.ViewHolder(view , recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull DemandeAdapter.ViewHolder holder, int position) {

        DemandeModel DemandeModel = demandeList.get(position);

        holder.desc.setText(DemandeModel.desc);
        holder.poids.setText(DemandeModel.poids);
        holder.taille.setText(DemandeModel.taille);
        holder.etat.setText(DemandeModel.etat);
        holder.DemandeID.setText(DemandeModel.DemandeID);

    }


    @Override
    public int getItemCount() {
        return demandeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView poids ,taille ,desc ,etat , DemandeID ;
        public ViewHolder(@NonNull View itemView , RecycleViewInterface recycleViewInterface) {
            super(itemView);


            poids = itemView.findViewById(R.id.mes_depart);
            taille = itemView.findViewById(R.id.mes_arrivee);
            desc = itemView.findViewById(R.id.date);
            etat = itemView.findViewById(R.id.idchemin);
            DemandeID = itemView.findViewById(R.id.demandeid);

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
