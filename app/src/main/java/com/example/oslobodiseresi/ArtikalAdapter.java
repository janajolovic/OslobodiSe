package com.example.oslobodiseresi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ArtikalAdapter extends RecyclerView.Adapter<ArtikalAdapter.ViewHolder> {

    ArrayList<Artikal> artikli = new ArrayList<>();
    private Context context;
    public ArtikalAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artikal_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtView.setText(artikli.get(position).getNaziv());
        Glide.with(context)
                .asBitmap()
                .load(artikli.get(position).getUrlSlike())
                .into(holder.imgView);
    }

    @Override
    public int getItemCount() {
        return artikli.size();
    }

    public void setArtikli(ArrayList<Artikal> artikli) {
        this.artikli = artikli;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgView;
        private TextView txtView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.artikalImage);
            txtView = itemView.findViewById(R.id.artikalNaziv);
        }
    }
}

