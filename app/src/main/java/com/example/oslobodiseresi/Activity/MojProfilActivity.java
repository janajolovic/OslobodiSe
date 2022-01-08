package com.example.oslobodiseresi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MojProfilActivity extends ToolbarNavigacijaSetup {

    private TextView textView4;
    private NavigationView navigationView;
    private FloatingActionButton dodajArtikal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_moj_profil);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        textView4 = findViewById(R.id.textView4);

        if(Utils.getInstance().getJelUlogovan() == 1)
            textView4.setText(Utils.getInstance().getKorisnik().getIme());

        dodajArtikal = findViewById(R.id.dodajArtikal);
        dodajArtikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MojProfilActivity.this, NapraviArtikal.class);
                startActivity(intent);
            }
        });

        setToolbar(this);
    }

}