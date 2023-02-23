package com.example.wassali.LivClient;

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
        View view = LayoutInflater.from(context).inflate(R.layout.items_mesdemandes_client,parent,false);

        return new ViewHolder(view , recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DemandeModel demandeModel = demandeList.get(position);

        holder.etat.setText(demandeModel.etat);
        holder.desc.setText(demandeModel.desc);




    }

    @Override
    public int getItemCount() {
        return demandeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc,etat;
        public ViewHolder(@NonNull View itemView , RecycleViewInterface recycleViewInterface) {
            super(itemView);

            etat = itemView.findViewById(R.id.etat);
            desc = itemView.findViewById(R.id.desc);



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
