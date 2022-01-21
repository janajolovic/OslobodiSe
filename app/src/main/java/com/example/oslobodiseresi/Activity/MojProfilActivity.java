package com.example.oslobodiseresi.Activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

public class MojProfilActivity extends ToolbarNavigacijaSetup {

    private TextView ime;
    private TextView email;
    private TextView kontakt;
    private TextView ocena;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_moj_profil);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ime = findViewById(R.id.ime);
        email = findViewById(R.id.email);
        kontakt = findViewById(R.id.kontakt);
        ocena = findViewById(R.id.ocena);

        if(Utils.getInstance().jeUlogovan())
        {
            ime.setText(Utils.getInstance().getKorisnik().getIme());
            email.setText(Utils.getInstance().getKorisnik().getEmail());
            kontakt.setText(Utils.getInstance().getKorisnik().getBrojTelefona());
            ocena.setText(String.valueOf(Utils.getInstance().getKorisnik().getZbirOcena() / Utils.getInstance().getKorisnik().getBrojOcena()));
        }

        setToolbar(false);
    }

}