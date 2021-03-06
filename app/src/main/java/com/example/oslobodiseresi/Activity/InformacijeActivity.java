package com.example.oslobodiseresi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.google.android.material.navigation.NavigationView;

public class InformacijeActivity extends ToolbarNavigacijaSetup {

    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_informacije);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(false);
    }
}