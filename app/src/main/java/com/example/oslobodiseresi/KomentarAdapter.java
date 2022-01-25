package com.example.oslobodiseresi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oslobodiseresi.Activity.ArtikalActivity;
import com.example.oslobodiseresi.Activity.ProfilKorisnika;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Komentar;
import com.example.oslobodiseresi.Retrofit.ItemRepository;

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

        holder.izbrisi.setVisibility(View.INVISIBLE);

        if(Utils.getInstance().jeUlogovan() && Utils.getInstance().getKorisnik().getId().equals(komentari.get(position).getKorisnik().getId())) {
            holder.izbrisi.setVisibility(View.VISIBLE);
            holder.izbrisi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Da li ste sigurni da zelite da obrisete ovaj komentar?");
                    builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MutableLiveData<String> mld = ItemRepository.getInstance(MainApplication.apiManager).IzbrisiKomentar(komentari.get(position).getId());
                            mld.observe((AppCompatActivity)context, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    komentari.remove(position);
                                    notifyItemRemoved(position);
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
        }

        holder.txtIme.setText(komentari.get(position).getKorisnik().getIme());

    }

    @Override
    public int getItemCount() {
        return komentari.size();
    }

    public void setKomentari(ArrayList<Komentar> komentari) {
        this.komentari = komentari;
        notifyDataSetChanged();
    }

    public ArrayList<Komentar> getKomentari() {
        return komentari;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProfil;
        private TextView txtIme;
        private TextView txtBrojGlasova;
        private TextView txtSadrzaj;
        private ImageView imgLajk;
        private TextView izbrisi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            izbrisi = itemView.findViewById(R.id.txtIzbrisi);
            imgProfil = itemView.findViewById(R.id.imgProfil);
            txtIme = itemView.findViewById(R.id.ime);
            txtBrojGlasova = itemView.findViewById(R.id.brojGlasova);
            txtSadrzaj = itemView.findViewById(R.id.komentar);
            imgLajk = itemView.findViewById(R.id.thumbUp);
        }
    }
}
