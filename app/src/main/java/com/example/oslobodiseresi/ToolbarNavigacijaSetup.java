package com.example.oslobodiseresi;

import android.content.Context;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.oslobodiseresi.Activity.InformacijeActivity;
import com.example.oslobodiseresi.Activity.LoginActivity;
import com.example.oslobodiseresi.Activity.MainActivity;
import com.example.oslobodiseresi.Activity.MojProfilActivity;
import com.example.oslobodiseresi.Activity.MojiOglasiActivity;
import com.example.oslobodiseresi.Activity.RegistracijaActivity;
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
                if(Utils.getInstance().jeUlogovan()){
                    intent = new Intent(this, MojProfilActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "Morate biti prijavljeni da biste pristupili Vasem profilu", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.mojiOglasi:
                if(Utils.getInstance().jeUlogovan()){
                    intent = new Intent(this, MojiOglasiActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "Morate biti prijavljeni da biste pristupili Vasim oglasima", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.omiljeniOglasi:
                if(Utils.getInstance().jeUlogovan()){
                    intent = new Intent(this, MojiOglasiActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(this, "Morate biti prijavljeni da biste pristupili omiljenim oglasima", Toast.LENGTH_SHORT).show();
                }
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


    public void setToolbar(boolean search){
        Context context = this;
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
                PopupMenu popupMenu = new PopupMenu(context, imgPrijava);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) { if (Utils.getInstance().getKorisnik() != null) {
                        switch(item.getItemId()) {
                            case R.id.moj_profil:
                                startActivity(new Intent(context, MojProfilActivity.class));
                                return true;
                            case R.id.odjava:
                                Utils.getInstance().setKorisnik(null);
                                startActivity(new Intent(context, MojProfilActivity.class));
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                                return true;
                        }
                    } else {
                        switch(item.getItemId()) {
                            case R.id.prijava:
                                startActivity(new Intent(context, LoginActivity.class));
                                return true;
                            case R.id.registracija:
                                startActivity(new Intent(context, RegistracijaActivity.class));
                                return true;
                        }
                    }
                        return false;
                    }
                });
                MenuInflater inflater = getMenuInflater();
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.prijava_registracija, popupMenu.getMenu());
                popupMenu.show();



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
