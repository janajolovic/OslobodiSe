package com.example.oslobodiseresi;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.oslobodiseresi.Activity.InformacijeActivity;
import com.example.oslobodiseresi.Activity.LoginActivity;
import com.example.oslobodiseresi.Activity.MainActivity;
import com.example.oslobodiseresi.Activity.MojProfilActivity;
import com.example.oslobodiseresi.Activity.MojiOglasiActivity;
import com.example.oslobodiseresi.Activity.OmiljeniOglasiActivity;
import com.example.oslobodiseresi.Activity.RegistracijaActivity;
import com.example.oslobodiseresi.Activity.ZaDavuda;
import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Kategorija;
import com.google.android.material.navigation.NavigationView;
import com.example.oslobodiseresi.Retrofit.ItemRepository;

import java.util.ArrayList;

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
                    intent = new Intent(this, OmiljeniOglasiActivity.class);
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

    private int gradId;
    private int kategorijaId;

    public int getGradId() {
        return gradId;
    }

    public int getKategorijaId() {
        return kategorijaId;
    }

    ConstraintLayout dodatak;
    Spinner spinnerKategorije;
    Spinner spinnerGradovi;

    public void setToolbar(boolean search){
        Context context = this;

        dodatak = findViewById(R.id.dodatak);
        dodatak.setVisibility(View.GONE);

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
                    public boolean onMenuItemClick(MenuItem item) {
                        if (Utils.getInstance().jeUlogovan()) {
                            switch(item.getItemId()) {
                                case R.id.moj_profil:
                                    startActivity(new Intent(context, MojProfilActivity.class));
                                    return true;
                                case R.id.odjava:
                                    Utils.getInstance().SacuvajKorisnika(null);

                                    startActivity(new Intent(context, MainActivity.class));
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
                if(Utils.getInstance().jeUlogovan()){
                    inflater.inflate(R.menu.moj_profil_menu, popupMenu.getMenu());
                }else{
                    inflater.inflate(R.menu.prijava_registracija, popupMenu.getMenu());
                }
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
        ImageView imgFilter = findViewById(R.id.imgFilter);
        if(search)
        {
            searchView.setVisibility(View.VISIBLE);
            //todo da ikonica bude sa desne strane
//            EditText searchEditText = (EditText) searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//            TypedValue typedValue = new TypedValue();
//            Resources.Theme theme = context.getTheme();
//            theme.resolveAttribute(R.attr.colorOnPrimary, typedValue, true);
//            @ColorInt int color = typedValue.data;
//            searchEditText.setTextColor(getResources().getColor(R.color.white));
//            searchEditText.setHintTextColor(getResources().getColor(R.color.white));

            imgFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dodatak.getVisibility() == View.GONE) {
                        dodatak.setVisibility(View.VISIBLE);
                    }
                    else{
                        dodatak.setVisibility(View.GONE);
                    }
                    spinnerKategorije = findViewById(R.id.spinnerKategorije);
                    spinnerGradovi = findViewById(R.id.spinnerGradovi);

                    Button primeniFiltereDugme = findViewById(R.id.primeniFiltere);

                    MutableLiveData<ArrayList<Kategorija>> mldKategorije = ItemRepository.getInstance(MainApplication.apiManager).getAllKategorije();
                    mldKategorije.observe(ToolbarNavigacijaSetup.this, new Observer<ArrayList<Kategorija>>() {
                        @Override
                        public void onChanged(ArrayList<Kategorija> kategorija) {
                            ArrayList<String> kategorije = new ArrayList<>();
                            kategorije.add("Sve kategorije");
                            for (Kategorija k : mldKategorije.getValue()) {
                                kategorije.add(k.getNaziv());
                            }
                            ArrayAdapter<String> kategorijeAdapter = new ArrayAdapter<>(ToolbarNavigacijaSetup.this, R.layout.spinner_na_plavo, kategorije);
                            kategorijeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerKategorije.setAdapter(kategorijeAdapter);
                        }
                    });


                    MutableLiveData<ArrayList<Grad>> mldGradovi = ItemRepository.getInstance(MainApplication.apiManager).getAllGradovi();
                    mldGradovi.observe(ToolbarNavigacijaSetup.this, new Observer<ArrayList<Grad>>() {
                        @Override
                        public void onChanged(ArrayList<Grad> grad) {
                            ArrayList<String> gradovi = new ArrayList<>();
                            gradovi.add("Svi gradovi");
                            for (Grad g : mldGradovi.getValue()) {
                                gradovi.add(g.getNaziv());
                            }
                            ArrayAdapter<String> gradoviAdapter = new ArrayAdapter<>(ToolbarNavigacijaSetup.this, R.layout.spinner_na_plavo, gradovi);
                            gradoviAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerGradovi.setAdapter(gradoviAdapter);
                            //spinnerGradovi.setOnItemSelectedListener(MainActivity.this);
                        }
                    });

                    //todo prepraviti ovo
                    spinnerKategorije.setSelection(getKategorijaId());
                    spinnerGradovi.setSelection(getGradId());

                    primeniFiltereDugme.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            primeniFiltere();
                        }
                    });
                }
            });
        }
        else
        {
            searchView.setVisibility(View.GONE);
            imgFilter.setVisibility(View.GONE);
            dodatak.setVisibility(View.GONE);
        }
    }


    public void primeniFiltere()
    {
        kategorijaId = spinnerKategorije.getSelectedItemPosition();
        gradId = spinnerGradovi.getSelectedItemPosition();
        dodatak.setVisibility(View.GONE);
    }
}