package com.example.oslobodiseresi;

import static com.example.oslobodiseresi.ArtikalActivity.ARTIKAL_ID_KEY;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        holder.imgProfil.setImageBitmap(artikli.get(position).getSlika());

        holder.imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArtikalActivity.class);
                intent.putExtra(ARTIKAL_ID_KEY, artikli.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });

        holder.imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.isFav = !holder.isFav;
                if (holder.isFav) {
                    holder.imgFav.setImageResource(R.drawable.ic_heart);
                } else {
                    holder.imgFav.setImageResource(R.drawable.ic_prazno_srce);
                }
            }
        });
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
        private CardView parent;
        private ImageView imgProfil;
        private TextView txtView;
        private ImageView imgFav;

        private Boolean isFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            isFav = false;
            imgProfil = itemView.findViewById(R.id.artikalImage);
            txtView = itemView.findViewById(R.id.artikalNaziv);
            parent = itemView.findViewById(R.id.artikalParent);
            imgFav = itemView.findViewById(R.id.imgFav);
        }
    }
}

