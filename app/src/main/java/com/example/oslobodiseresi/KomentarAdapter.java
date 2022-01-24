package com.example.oslobodiseresi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Komentar;

import java.util.ArrayList;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.ViewHolder> {

    ArrayList<Komentar> komentari = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.komentar_layout, parent, false);
        return new KomentarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.imgLajk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.isLajk = !holder.isLajk;
                if(holder.isLajk){
                    holder.imgLajk.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                    if(holder.isDislajk){
                        holder.isDislajk = !holder.isDislajk;
                        holder.imgDislajk.setImageResource(R.drawable.ic_outline_thumb_down_24);
                    }
                } else {
                    holder.imgLajk.setImageResource(R.drawable.ic_outline_thumb_up_24);
                }
            }
        });

        holder.imgDislajk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.isDislajk = !holder.isDislajk;
                if(holder.isDislajk){
                    holder.imgDislajk.setImageResource(R.drawable.ic_baseline_thumb_down_24);
                    if(holder.isLajk){
                        holder.isLajk = !holder.isLajk;
                        holder.imgLajk.setImageResource(R.drawable.ic_outline_thumb_up_24);
                    }
                } else {
                    holder.imgDislajk.setImageResource(R.drawable.ic_outline_thumb_down_24);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return komentari.size();
    }

    public void setKomentari(ArrayList<Komentar> komentari) {
        this.komentari = komentari;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private boolean isLajk;
        private boolean isDislajk;

        private ImageView imgProfil;
        private TextView txtIme;
        private TextView txtBrojGlasova;
        private TextView txtSadrzaj;
        private ImageView imgLajk;
        private ImageView imgDislajk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            isLajk = false;
            isDislajk = false;

            imgProfil = itemView.findViewById(R.id.imgProfil);
            txtIme = itemView.findViewById(R.id.ime);
            txtBrojGlasova = itemView.findViewById(R.id.brojGlasova);
            txtSadrzaj = itemView.findViewById(R.id.komentar);
            imgLajk = itemView.findViewById(R.id.thumbUp);
            imgDislajk = itemView.findViewById(R.id.thumbDown);
        }
    }
}
