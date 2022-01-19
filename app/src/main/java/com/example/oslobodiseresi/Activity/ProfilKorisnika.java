package com.example.oslobodiseresi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Retrofit.ApiManager;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

public class ProfilKorisnika extends ToolbarNavigacijaSetup {

    private NavigationView navigationView;
    public static final String KORISNIK_ID = "korisnikId";
    private TextView txtKorisnik;
    private RatingBar rating;
    private String korisnikId;
    private ImageView imgKorisnik;
    private RecyclerView artikliKorisnik;

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

        MutableLiveData<Korisnik> mld = UserRepository.getInstance(MainApplication.apiManager).GetKorisnikById(korisnikId);
        mld.observe(ProfilKorisnika.this, new Observer<Korisnik>() {
            @Override
            public void onChanged(Korisnik korisnik) {
                txtKorisnik.setText(korisnik.getIme());
                rating.setRating((float)korisnik.getZbirOcena()/(float)korisnik.getBrojOcena());
            }
        });
    }
}