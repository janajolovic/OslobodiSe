package com.example.oslobodiseresi.Activity;

import android.os.Bundle;

import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.google.android.material.navigation.NavigationView;

public class OmiljeniOglasiActivity extends ToolbarNavigacijaSetup {

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_omiljeni_oglasi);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(this, true);
    }
}