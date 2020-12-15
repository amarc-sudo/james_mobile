package com.iut.james_mobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.iut.james_mobile.apiobject.Etudiant;
import com.iut.james_mobile.apiobject.Professeur;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class RecyclerSimpleViewAdapter extends RecyclerView.Adapter<RecyclerSimpleViewAdapter.EtudiantViewHolder> {

    private List<Etudiant> items;

    private int itemLayout;

    private Professeur professeurConnecte;

    private Map<Etudiant,Spinner> SP_presences = new HashMap<>();

    private String formationSelectionne;

    public RecyclerSimpleViewAdapter(List<Etudiant> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    public void setProfesseurConnecte(Professeur professeurConnecte) {
        this.professeurConnecte = professeurConnecte;
    }

    @Override
    public EtudiantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_etudiant, parent, false);
        return new EtudiantViewHolder(v);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(EtudiantViewHolder holder, int position) {
        Etudiant etudiant = items.get(position);
        holder.primaryText.setText(etudiant.getPersonne().getNom() + " "+etudiant.getPersonne().getPrenom());
        if (etudiant.isHasSigned()==true)
            holder.BT_signature.setEnabled(false);
        else{
            holder.BT_signature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(holder.context,SignatureActivity.class);
                    intent.putExtra("etudiant", etudiant);
                    intent.putExtra("professeur", professeurConnecte);
                    intent.putExtra("formation",formationSelectionne);
                    holder.context.startActivity(intent);
                }
            });
        }
        SP_presences.put(etudiant,holder.SP_presence);
        holder.itemView.setTag(etudiant);
    }

    public void setFormationSelectionne(String intituleFormationSelectionne) {
        this.formationSelectionne=intituleFormationSelectionne;
    }

    public static class EtudiantViewHolder extends RecyclerView.ViewHolder {
        public TextView primaryText;

        public Button BT_signature;

        public Spinner SP_presence;

        public Context context;

        public EtudiantViewHolder(View itemView) {
            super(itemView);
            primaryText = (TextView) itemView.findViewById(R.id.TV_eleve);
            BT_signature=(Button)itemView.findViewById(R.id.BT_Signature);
            SP_presence=itemView.findViewById(R.id.SP_presence);
            context=itemView.getContext();
        }
    }

}
