package com.example.oslobodiseresi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oslobodiseresi.Activity.ArtikalActivity;
import com.example.oslobodiseresi.Activity.ProfilKorisnika;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Komentar;

import java.util.ArrayList;

public class KomentarAdapter extends RecyclerView.Adapter<KomentarAdapter.ViewHolder> {

    private Context context;
    ArrayList<Komentar> komentari = new ArrayList<>();

    public KomentarAdapter(Context context) {
        this.context = context;
    }

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
                komentari.get(position).setLajkovan(!komentari.get(position).isLajkovan());
                if(komentari.get(position).isLajkovan()){
                    holder.imgLajk.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                    komentari.get(position).setBrojLajkova(komentari.get(position).getBrojLajkova()+1);
                } else {
                    holder.imgLajk.setImageResource(R.drawable.ic_outline_thumb_up_24);
                    komentari.get(position).setBrojLajkova(komentari.get(position).getBrojLajkova()-1);
                }
                notifyItemChanged(position);
            }
        });

        holder.txtIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfilKorisnika.class);
                intent.putExtra("KORISNIK_ID", komentari.get(position).getKorisnik().getId());
                context.startActivity(intent);
            }
        });

        holder.imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfilKorisnika.class);
                intent.putExtra("KORISNIK_ID", komentari.get(position).getKorisnik().getId());
                context.startActivity(intent);
            }
        });

        holder.txtBrojGlasova.setText(String.valueOf(komentari.get(position).getBrojLajkova()));

        holder.txtSadrzaj.setText(komentari.get(position).getSadrzaj());
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
        private ImageView imgProfil;
        private TextView txtIme;
        private TextView txtBrojGlasova;
        private TextView txtSadrzaj;
        private ImageView imgLajk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProfil = itemView.findViewById(R.id.imgProfil);
            txtIme = itemView.findViewById(R.id.ime);
            txtBrojGlasova = itemView.findViewById(R.id.brojGlasova);
            txtSadrzaj = itemView.findViewById(R.id.komentar);
            imgLajk = itemView.findViewById(R.id.thumbUp);
        }
    }
}
