package com.example.oslobodiseresi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.oslobodiseresi.Activity.MojProfilActivity;
import com.example.oslobodiseresi.Activity.ProfilKorisnika;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Komentar;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

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

        if(komentari.get(position).isLajkovan()) {
            holder.imgLajk.setImageResource(R.drawable.ic_baseline_thumb_up_24);
        } else {
            holder.imgLajk.setImageResource(R.drawable.ic_outline_thumb_up_24);
        }

        holder.imgLajk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRepository.getInstance(MainApplication.apiManager).LajkujKomentar(komentari.get(position).getId(), Utils.getInstance().getKorisnik().getId());
                komentari.get(position).setLajkovan(!komentari.get(position).isLajkovan());
                if(komentari.get(position).isLajkovan()){
                    holder.imgLajk.setImageResource(R.drawable.ic_baseline_thumb_up_24);
                    komentari.get(position).setBrojLajkova(komentari.get(position).getBrojLajkova()+1);
                    Utils.getInstance().getKorisnik().getLajkovaniKomentari().add(komentari.get(position).getId());
                } else {
                    holder.imgLajk.setImageResource(R.drawable.ic_outline_thumb_up_24);
                    komentari.get(position).setBrojLajkova(komentari.get(position).getBrojLajkova()-1);
                    Utils.getInstance().getKorisnik().getLajkovaniKomentari().remove(Integer.valueOf(komentari.get(position).getId()));
                }
                Utils.getInstance().SacuvajKorisnika(Utils.getInstance().getKorisnik());
                notifyItemChanged(position);
            }
        });

        holder.txtIme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.getInstance().jeUlogovan()&&Utils.getInstance().getKorisnik().getId().equals(komentari.get(position).getKorisnik().getId())){
                    Intent intent = new Intent(context, MojProfilActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ProfilKorisnika.class);
                    intent.putExtra("KORISNIK_ID", komentari.get(position).getKorisnik().getId());
                    context.startActivity(intent);
                }
            }
        });

        MutableLiveData<ResponseBody> mld = UserRepository.getInstance(MainApplication.apiManager).GetProfilna(Utils.getInstance().getKorisnik().getId());
        mld.observe((AppCompatActivity) context, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                holder.imgProfil.setImageBitmap(bmp);
            }
        });

        holder.imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.getInstance().jeUlogovan()&&Utils.getInstance().getKorisnik().getId().equals(komentari.get(position).getKorisnik().getId())){
                    Intent intent = new Intent(context, MojProfilActivity.class);
                    context.startActivity(intent);
                } else {
                    Intent intent = new Intent(context, ProfilKorisnika.class);
                    intent.putExtra("KORISNIK_ID", komentari.get(position).getKorisnik().getId());
                    context.startActivity(intent);
                }
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
                            ItemRepository.getInstance(MainApplication.apiManager).IzbrisiKomentar(komentari.get(position).getId());
                            komentari.remove(position);
                            notifyItemRemoved(position);
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
        private CircleImageView imgProfil;
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
