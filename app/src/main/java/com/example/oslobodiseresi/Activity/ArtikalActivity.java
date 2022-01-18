package com.example.oslobodiseresi.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.oslobodiseresi.ArtikalAdapter;
import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ArtikalActivity extends ToolbarNavigacijaSetup {

    public static final String ARTIKAL_ID_KEY = "artikalId";
    private TextView txtOpis, txtKategorija, txtGrad, txtKorisnik;
    private ImageView img;
    private TextView txtNaziv;
    private ImageView artikalBack;
    private ImageView imgFav;
    private Boolean isFav;
    private NavigationView navigationView;
    private TextView txtKontakt;
    private Item artikal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_artikal);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(false);

        initViews();
        Intent intent = getIntent();

        if(null != intent)
        {
            String json = intent.getStringExtra(ARTIKAL_ID_KEY);
            if(json != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<Item>() {}.getType();
                artikal = gson.fromJson(json, type);
                setData();

                if(!Utils.getInstance().jeUlogovan()){ {
                    imgFav.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(ArtikalActivity.this, "Morate biti prijavljeni da biste oznacili oglas", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                }

                Context context = ArtikalActivity.this;
                txtKorisnik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ProfilKorisnika.class);
                        intent.putExtra("KORISNIK_ID", artikal.getUserId());
                        startActivity(intent);
                    }
                });
                imgFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Utils.getInstance().jeUlogovan()){
                            isFav = !isFav;
                            if (isFav) {
                                imgFav.setImageResource(R.drawable.ic_heart);
                                MutableLiveData<String> mld = UserRepository.getInstance(MainApplication.apiManager).DodajOmiljeniOglas(Utils.getInstance().getKorisnik().getId(), artikal.getId());
                                mld.observe((AppCompatActivity)context, new Observer<String>() {
                                    @Override
                                    public void onChanged(String s) {
                                        Toast.makeText(context, "Artikal je dodat u omiljene oglase", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                imgFav.setImageResource(R.drawable.ic_prazno_srce);
                                MutableLiveData<String> mld = UserRepository.getInstance(MainApplication.apiManager).IzbrisiOmiljeniOglas(Utils.getInstance().getKorisnik().getId(), artikal.getId());
                                mld.observe((AppCompatActivity)context, new Observer<String>() {
                                    @Override
                                    public void onChanged(String s) {
                                        Toast.makeText(context, "Artikal je uklonjen iz omiljenih oglasa", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(context, "Morate biti prijavljeni da biste oznacili oglas", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                artikalBack = findViewById(R.id.artikalBack);
                artikalBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ArtikalActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                if(Utils.getInstance().jeUlogovan()){
                    MutableLiveData<ArrayList<Item>> mld = UserRepository.getInstance(MainApplication.apiManager).GetOmiljeniOglasiFromUser(artikal.getUserId());
                    mld.observe(ArtikalActivity.this, new Observer<ArrayList<Item>>() {
                        @Override
                        public void onChanged(ArrayList<Item> items) {
                            isFav = items.contains(artikal.getId());
                            if (isFav) {
                                imgFav.setImageResource(R.drawable.ic_heart);
                            } else {
                                imgFav.setImageResource(R.drawable.ic_prazno_srce);
                            }
                            imgFav.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    isFav = !isFav;
                                    if (isFav) {
                                        imgFav.setImageResource(R.drawable.ic_heart);
                                        UserRepository.getInstance(MainApplication.apiManager).DodajOmiljeniOglas(Utils.getInstance().getKorisnik().getId(), artikal.getId());
                                    } else {
                                        imgFav.setImageResource(R.drawable.ic_prazno_srce);
                                        UserRepository.getInstance(MainApplication.apiManager).IzbrisiOmiljeniOglas(Utils.getInstance().getKorisnik().getId(), artikal.getId());
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    private void setData() {
        txtOpis.setText(artikal.getOpis());
        txtNaziv.setText(artikal.getNaziv());
        txtKategorija.setText(artikal.getKategorija().getNaziv());
        txtGrad.setText(artikal.getGrad().getNaziv());
        txtKorisnik.setText(artikal.getUser().getIme());
//      txtKontakt.setText(Utils.getInstance().getKorisnik().getBrojTelefona());
//        Glide.with(this)
//                .asBitmap()
//                .load(artikal.getSlika())
//                .into(img);
    }

    private void initViews() {
        txtOpis = findViewById(R.id.opis);
        img = findViewById(R.id.imgOpsirno);
        txtNaziv = findViewById(R.id.txtNaziv);
        txtKategorija = findViewById(R.id.txtKategorija);
        txtGrad = findViewById(R.id.txtGrad);
        txtKorisnik = findViewById(R.id.txtKorisnik);
        txtKontakt = findViewById(R.id.txtKontakt);
        imgFav = findViewById(R.id.imgFav);
    }

    private void setViews() {


    }
}