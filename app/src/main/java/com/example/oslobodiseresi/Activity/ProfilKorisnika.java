package com.example.oslobodiseresi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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

    float rate = 0;

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
        artikliKorisnik.setLayoutManager(new GridLayoutManager(this, 2));


        MutableLiveData<Korisnik> mld = UserRepository.getInstance(MainApplication.apiManager).GetKorisnikById(korisnikId);
        mld.observe(ProfilKorisnika.this, new Observer<Korisnik>() {
            @Override
            public void onChanged(Korisnik korisnik) {
                txtKorisnik.setText(korisnik.getIme());
                brojKorisnika.setText(korisnik.getBrojTelefona());
                if (korisnik.getBrojOcena() == 0) {
                    prosecnaOcena.setText("Korisnik nije ocenjen");
                } else {
                    prosecnaOcena.setText(korisnik.getZbirOcena() / korisnik.getBrojOcena());
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
}