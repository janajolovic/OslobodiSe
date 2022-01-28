package com.example.oslobodiseresi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oslobodiseresi.ArtikalAdapter;
import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Retrofit.ApiManager;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import okhttp3.ResponseBody;

public class ProfilKorisnika extends ToolbarNavigacijaSetup {

    private NavigationView navigationView;
    public static final String KORISNIK_ID = "KORISNIK_ID";
    private TextView txtKorisnik;
    private RatingBar rating;
    private String korisnikId;
    private ImageView imgKorisnik;
    private RecyclerView artikliKorisnik;
    private TextView brojKorisnika;
    private TextView prosecnaOcena;
    private ArtikalAdapter adapterArtikli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_profil_korisnika);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(false);

        Intent intent = getIntent();

        if(intent != null)
        {
            String idKorisnik = intent.getStringExtra(KORISNIK_ID);
            korisnikId = idKorisnik;
        }

        txtKorisnik = findViewById(R.id.imeKorisnika);
        rating = findViewById(R.id.ratingBar);
        brojKorisnika = findViewById(R.id.brojKorisnika);
        artikliKorisnik = findViewById(R.id.artikliKorisnik);
        adapterArtikli = new ArtikalAdapter(this);
        prosecnaOcena = findViewById(R.id.prosecnaOcena);
        artikliKorisnik.setLayoutManager(new GridLayoutManager(this, 2));
        imgKorisnik = findViewById(R.id.imgKorisnik);

        if(!Utils.getInstance().jeUlogovan()){
            rating.setEnabled(false);
        }

        MutableLiveData<String> mld2 = UserRepository.getInstance(MainApplication.apiManager).GetProfilna(korisnikId);
        mld2.observe(ProfilKorisnika.this, new Observer<String>() {
            @Override
            public void onChanged(String str) {
                byte[] bajtovi = Base64.decode(str, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bajtovi,0,bajtovi.length);
                imgKorisnik.setImageBitmap(bitmap);
            }
        });

        MutableLiveData < Korisnik > mld = UserRepository.getInstance(MainApplication.apiManager).GetKorisnikById(korisnikId);
        mld.observe(ProfilKorisnika.this, new Observer<Korisnik>() {
            @Override
            public void onChanged(Korisnik korisnik) {
                txtKorisnik.setText(korisnik.getIme());
                brojKorisnika.setText(korisnik.getBrojTelefona());
                if (korisnik.getBrojOcena() == 0) {
                    prosecnaOcena.setText("/");
                } else {
                    prosecnaOcena.setText(String.valueOf(korisnik.getZbirOcena() / korisnik.getBrojOcena()));
                }
            }
        });

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (Utils.getInstance().jeUlogovan()) {
                    MutableLiveData<Float> ocena = UserRepository.getInstance(MainApplication.apiManager).DodajOcenu(korisnikId, Utils.getInstance().getKorisnik().getId(), rating);
                    ocena.observe(ProfilKorisnika.this, new Observer<Float>() {
                        @Override
                        public void onChanged(Float s) {
                            prosecnaOcena.setText(String.valueOf(ocena.getValue()));
                        }
                    });
                }else{
                    Toast.makeText(ProfilKorisnika.this, "Morate biti ulogovani da bi ocenjivali korisnike", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MutableLiveData<ArrayList<Item>> artikli = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromUser(korisnikId);
        artikli.observe(ProfilKorisnika.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                adapterArtikli.setArtikli(artikli.getValue());
                artikliKorisnik.setAdapter(adapterArtikli);
            }
        });
    }

    @Override
    public void primeniFiltere() {
        super.primeniFiltere();

        MutableLiveData<ArrayList<Item>> mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromUser(korisnikId);
        mld.observe(ProfilKorisnika.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                ArrayList<Item> artikli = new ArrayList<>();
                if (getKategorijaId() != 0 && getGradId() != 0) {
                    for(Item i:items){
                        if(i.getKategorijaId()==getKategorijaId() && i.getGradId()==getGradId()){
                            artikli.add(i);
                        }
                    }
                }else if(getKategorijaId() == 0 && getGradId() == 0){
                    artikli = items;
                }else if(getKategorijaId() != 0){
                    for(Item i:items){
                        if(i.getKategorijaId()==getKategorijaId()){
                            artikli.add(i);
                        }
                    }
                }else{
                    for(Item i:items){
                        if(i.getGradId()==getGradId()){
                            artikli.add(i);
                        }
                    }
                }
                adapterArtikli.setArtikli(artikli);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        MutableLiveData<ArrayList<Item>> mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromUser(korisnikId);
        mld.observe(this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                ArrayList<Item> artikli = mld.getValue();
                adapterArtikli.setArtikli(artikli);
                artikliKorisnik.setAdapter(adapterArtikli);
            }
        });
    }
}