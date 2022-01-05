package com.example.oslobodiseresi;

import static com.example.oslobodiseresi.ArtikalActivity.ARTIKAL_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerArtikli;
    private ArrayList<Artikal> artikli = new ArrayList<>();
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        recyclerArtikli = findViewById(R.id.artikli);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(this);

        //recycler view
        ArtikalAdapter adapterArtikli = new ArtikalAdapter(this);
        adapterArtikli.setArtikli(Utils.getInstance().getArtikli());

        recyclerArtikli.setAdapter(adapterArtikli);
        recyclerArtikli.setLayoutManager(new GridLayoutManager(this, 2));


    }

    //todo sve funkcije ispod moraju da se kopiraju na sve ostale aktivitije, naci nacin da se ovo izbegne
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
        }
        //close navigation drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    public void setToolbar(Context context){
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
                startActivity(new Intent(context, LoginActivity.class));
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
    }

}
