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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class RecyclerSimpleViewAdapter extends RecyclerView.Adapter<RecyclerSimpleViewAdapter.EtudiantViewHolder> {

    /**
     * List items
     */
    private List<Etudiant> items;
    /**
     * the resource id of item Layout
     */
    private int itemLayout;

    private Professeur professeurConnecte;

    private Map<Etudiant,Spinner> SP_presences = new HashMap<>();

    private String formationSelectionne;

    /**
     * Constructor RecyclerSimpleViewAdapter
     * @param items : the list items
     * @param itemLayout : the resource id of itemView
     */
    public RecyclerSimpleViewAdapter(List<Etudiant> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    public void setProfesseurConnecte(Professeur professeurConnecte) {
        this.professeurConnecte = professeurConnecte;
    }

    /**
     * Create View Holder by Type
     * @param parent, the view parent
     * @param viewType : the type of View
     */
    @Override
    public EtudiantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get inflater and get view by resource id itemLayout

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_etudiant, parent, false);
        // return ViewHolder with View
        return new EtudiantViewHolder(v);
    }

    /**
     * Get the size of items in adapter
     * @return the size of items in adapter
     */
    @Override
    public int getItemCount() {
        return items.size();
    }
    /**
     * Bind View Holder with Items
     * @param holder: the view holder
     * @param position : the current position
     */
    @Override
    public void onBindViewHolder(EtudiantViewHolder holder, int position) {
        // find item by position
        Etudiant etudiant = items.get(position);
        // save information in holder, we have one type in this adapter
        holder.primaryText.setText(etudiant.getPersonne().getNom() + " "+etudiant.getPersonne().getPrenom());
        if (etudiant.isSignature()==true)
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

    /**
     *
     * Class viewHolder
     * Hold an textView
     */
    public static class EtudiantViewHolder extends RecyclerView.ViewHolder {
        // TextViex
        public TextView primaryText;

        public Button BT_signature;

        public Spinner SP_presence;

        public Context context;

        /**
         * Constructor ViewHolder
         * @param itemView: the itemView
         */
        public EtudiantViewHolder(View itemView) {
            super(itemView);
            // link primaryText
            primaryText = (TextView) itemView.findViewById(R.id.TV_eleve);
            BT_signature=(Button)itemView.findViewById(R.id.BT_Signature);
            SP_presence=itemView.findViewById(R.id.SP_presence);
            context=itemView.getContext();


        }
    }


}
