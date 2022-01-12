package com.example.oslobodiseresi;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.oslobodiseresi.Activity.InformacijeActivity;
import com.example.oslobodiseresi.Activity.LoginActivity;
import com.example.oslobodiseresi.Activity.MainActivity;
import com.example.oslobodiseresi.Activity.MojProfilActivity;
import com.example.oslobodiseresi.Activity.MojiOglasiActivity;
import com.example.oslobodiseresi.Activity.OmiljeniOglasiActivity;
import com.example.oslobodiseresi.Activity.ZaDavuda;
import com.google.android.material.navigation.NavigationView;

public class ToolbarNavigacijaSetup extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.pocetna:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            //TODO: dovrsiti
            case R.id.mojProfil:
                intent = new Intent(this, MojProfilActivity.class);
                startActivity(intent);
                break;
            case R.id.mojiOglasi:
                intent = new Intent(this, MojiOglasiActivity.class);
                startActivity(intent);
                break;
            case R.id.omiljeniOglasi:
                intent = new Intent(this, OmiljeniOglasiActivity.class);
                startActivity(intent);
                break;
            case R.id.davud:
                intent = new Intent(this, ZaDavuda.class);
                startActivity(intent);
                break;
            case R.id.informacije:
                intent = new Intent(this, InformacijeActivity.class);
                startActivity(intent);
                break;
        }
        //close navigation drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public void setToolbar(Context context, boolean search){
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ImageView back;
        ImageView hamburger;
        ImageView  imgPrijava;
        //prijava
        imgPrijava = findViewById(R.id.imgProfil);
        imgPrijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.getInstance().getKorisnik() != null) {

                } else {
                    startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        //todo search

        //hamburger
        hamburger = findViewById(R.id.imgHamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer((GravityCompat.START));
            }
        });
        //back
        back = navigationView.getHeaderView(0).findViewById(R.id.navBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        SearchView searchView = findViewById(R.id.search_bar);
        if(search)
        {
            searchView.setVisibility(View.VISIBLE);
            //todo da ikonica bude sa desne strane
        }
        else
            searchView.setVisibility(View.GONE);
    }
}
