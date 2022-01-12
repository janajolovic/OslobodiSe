package com.example.oslobodiseresi;

import static com.example.oslobodiseresi.Activity.ArtikalActivity.ARTIKAL_ID_KEY;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oslobodiseresi.Activity.ArtikalActivity;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Retrofit.ItemRepository;

import java.util.ArrayList;
import java.util.Locale;


public class ArtikalAdapter extends RecyclerView.Adapter<ArtikalAdapter.ViewHolder> implements Filterable {

    ArrayList<Item> artikli = new ArrayList<>();
    ArrayList<Item> artikliFull = new ArrayList<>();
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

        if(Utils.getInstance().getKorisnik() == null)
            holder.imgDelete.setVisibility(View.INVISIBLE);
        else if(Utils.getInstance().getKorisnik().getId() == artikli.get(position).getUserId())
            holder.imgDelete.setVisibility(View.VISIBLE);


        holder.txtNaziv.setText(artikli.get(position).getNaziv());
//        holder.imgProfil.setImageBitmap(artikli.get(position).getSlika());

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

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Da li ste sigurni da zelite da obrisete " + holder.txtNaziv.getText().toString() + "?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ItemRepository.getInstance(MainApplication.apiManager).DeleteItem(artikli.get(position).getId());
                        Toast.makeText(context, "obrisano", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "nista", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return artikli.size();
    }

    public void setArtikli(ArrayList<Item> artikli) {
        this.artikli = artikli;
        artikliFull = new ArrayList<>(artikli);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return artikalFilter;
    }

    private Filter artikalFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Item> filteredArtikli = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
                filteredArtikli.addAll(artikliFull);
            else
            {
                String filteredPattern = constraint.toString().toLowerCase().trim();
                for(Item item : filteredArtikli)
                    if(item.getNaziv().toLowerCase().trim().contains(filteredPattern))
                        filteredArtikli.add(item);
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredArtikli;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            artikli.clear();
            artikli.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView imgProfil;
        private TextView txtNaziv;
        private ImageView imgFav;
        private ImageView imgDelete;

        private Boolean isFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            isFav = false;
            imgProfil = itemView.findViewById(R.id.artikalImage);
            txtNaziv = itemView.findViewById(R.id.artikalNaziv);
            parent = itemView.findViewById(R.id.artikalParent);
            imgFav = itemView.findViewById(R.id.imgFav);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}

