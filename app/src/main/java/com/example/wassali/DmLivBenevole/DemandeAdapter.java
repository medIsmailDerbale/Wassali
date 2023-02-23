package com.example.wassali.DmLivBenevole;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wassali.Chemins.RecycleViewInterface;
import com.example.wassali.R;

import java.util.ArrayList;


public class DemandeAdapter extends RecyclerView.Adapter<DemandeAdapter.ViewHolder> {


    private final RecycleViewInterface recycleViewInterface;

    Context context;
    ArrayList<DemandeModel> demandeList;

    public DemandeAdapter( RecycleViewInterface recycleViewInterface , Context context, ArrayList<DemandeModel> cheminList ) {
        this.context = context;
        this.demandeList = cheminList;
        this.recycleViewInterface = recycleViewInterface;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_demande_benevole,parent,false);

        return new ViewHolder(view , recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DemandeModel demandeModel = demandeList.get(position);

        holder.nomprenom.setText(demandeModel.nomprenom);
        holder.colis.setText(demandeModel.desc);
        holder.etat.setText(demandeModel.etat);

    }



    @Override
    public int getItemCount() {
        return demandeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nomprenom,colis ,etat;
        public ViewHolder(@NonNull View itemView , RecycleViewInterface recycleViewInterface) {
            super(itemView);


            nomprenom = itemView.findViewById(R.id.nomprenomc);
            colis = itemView.findViewById(R.id.desc);
            etat = itemView.findViewById(R.id.etat);



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

