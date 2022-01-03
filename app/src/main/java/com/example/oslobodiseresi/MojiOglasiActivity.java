package com.example.oslobodiseresi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MojiOglasiActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private ImageView back;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TODO: promeniti layout
        setContentView(R.layout.activity_moji_oglasi);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        back = navigationView.getHeaderView(0).findViewById(R.id.navBack);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

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
        }
        //close navigation drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}

