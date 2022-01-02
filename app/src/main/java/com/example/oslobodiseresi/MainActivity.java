package com.example.oslobodiseresi;

import static com.example.oslobodiseresi.ArtikalActivity.ARTIKAL_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerArtikli;
    private ArrayList<Artikal> artikli = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView back;
    private ImageView hamburger;
    private ImageView  imgPrijava;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerArtikli = findViewById(R.id.artikli);

        drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_closed);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
//
        navigationView = findViewById(R.id.navView);
        back = navigationView.getHeaderView(0).findViewById(R.id.navBack);
        hamburger = findViewById(R.id.imgHamburger);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer((GravityCompat.START));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        //recycler view
        ArtikalAdapter adapterArtikli = new ArtikalAdapter(this);
        adapterArtikli.setArtikli(Utils.getInstance().getArtikli());

        recyclerArtikli.setAdapter(adapterArtikli);
        recyclerArtikli.setLayoutManager(new GridLayoutManager(this, 2));

        imgPrijava = findViewById(R.id.imgProfil);
        imgPrijava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//    }


}
