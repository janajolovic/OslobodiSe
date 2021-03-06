package com.example.oslobodiseresi;

import static com.example.oslobodiseresi.Activity.ArtikalActivity.ARTIKAL_ID_KEY;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oslobodiseresi.Activity.ArtikalActivity;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.google.gson.Gson;

import java.util.ArrayList;

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

        holder.imgDelete.setVisibility(View.INVISIBLE);
        if(Utils.getInstance().jeUlogovan() && (Utils.getInstance().getKorisnik().getId().equals(artikli.get(position).getUserId())))
            holder.imgDelete.setVisibility(View.VISIBLE);

        holder.txtNaziv.setText(artikli.get(position).getNaziv());
//        holder.imgProfil.setImageBitmap(artikli.get(position).getSlika());

        holder.imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArtikalActivity.class);
                intent.putExtra(ARTIKAL_ID_KEY, artikli.get(position).getId());

                context.startActivity(intent);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.getAdapterPosition()==-1){
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Da li ste sigurni da zelite da obrisete " + holder.txtNaziv.getText().toString() + "?");
                builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(holder.getAdapterPosition()==-1){
                            return;
                        }
                        MutableLiveData<String> mld = ItemRepository.getInstance(MainApplication.apiManager).DeleteItem(artikli.get(holder.getAdapterPosition()).getId());
                        mld.observe((AppCompatActivity)context, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                if(holder.getAdapterPosition()==-1){
                                    return;
                                }
                                artikli.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());
                            }
                        });
                    }
                });
                builder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
            }
        });
        if(artikli.get(holder.getAdapterPosition()).getSlika()!=null){
            byte[] bajtovi = Base64.decode(artikli.get(holder.getAdapterPosition()).getSlika(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bajtovi,0,bajtovi.length);
            holder.imgProfil.setImageBitmap(bitmap);
        }
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
                for(Item item : artikliFull)
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
        private ImageView imgProfil;
        private TextView txtNaziv;
        private ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProfil = itemView.findViewById(R.id.artikalImage);
            txtNaziv = itemView.findViewById(R.id.artikalNaziv);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}

