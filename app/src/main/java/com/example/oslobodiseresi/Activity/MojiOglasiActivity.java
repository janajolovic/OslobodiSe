package com.example.oslobodiseresi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oslobodiseresi.ArtikalAdapter;
import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import okhttp3.internal.Util;

public class MojiOglasiActivity extends ToolbarNavigacijaSetup {

    private NavigationView navigationView;
    private RecyclerView recyclerArtikli;
    private FloatingActionButton dodajArtikal;
    private ArtikalAdapter adapterArtikli;
    private ProgressBar progressBar;
    private ImageView imgNoResults;
    private TextView txtNoResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_moji_oglasi);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(true);

        imgNoResults = findViewById(R.id.imgNoResults);
        txtNoResults = findViewById(R.id.txtNoResults);

        recyclerArtikli = findViewById(R.id.mojiArtikli);

        adapterArtikli = new ArtikalAdapter(this);

        recyclerArtikli.setLayoutManager(new GridLayoutManager(this, 2));

        // ucitavanje oglasa prijavljenog korisnika
        MutableLiveData<ArrayList<Item>> artikli = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromUser(Utils.getInstance().getKorisnik().getId());
        artikli.observe(MojiOglasiActivity.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                if(items==null){
                    return;
                }
                progressBar.setVisibility(View.INVISIBLE);
                adapterArtikli.setArtikli(artikli.getValue());
                recyclerArtikli.setAdapter(adapterArtikli);
            }
        });

        // dugme za dodavanje novog oglasa
        dodajArtikal = findViewById(R.id.dodajArtikal);
        dodajArtikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MojiOglasiActivity.this, NapraviArtikal.class);
                startActivity(intent);
            }
        });

        // PRETRAGA
        SearchView searchView = findViewById(R.id.search_bar);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterArtikli.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapterArtikli.getFilter().filter("");
                return false;
            }
        });
        adapterArtikli.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (adapterArtikli.getItemCount() == 0) {
                    imgNoResults.setVisibility(View.VISIBLE);
                    txtNoResults.setVisibility(View.VISIBLE);
                } else {
                    imgNoResults.setVisibility(View.INVISIBLE);
                    txtNoResults.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    // filtriranje po gradovima ili kategorijama
    @Override
    public void primeniFiltere() {
        super.primeniFiltere();

        MutableLiveData<ArrayList<Item>> mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromUser(Utils.getInstance().getKorisnik().getId());
        mld.observe(MojiOglasiActivity.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                if(items==null){
                    return;
                }
                ArrayList<Item> artikli = new ArrayList<>();
                if (getKategorijaId() != 0 && getGradId() != 0) {
                    for(Item i:items){
                        if(i.getKategorijaId()==getKategorijaId() && i.getGradId()==getGradId()){
                            artikli.add(i);
                        }
                    }
                }else if(getKategorijaId() == 0 && getGradId() == 0){
                    artikli = items;
                }else if(getKategorijaId() != 0){
                    for(Item i:items){
                        if(i.getKategorijaId()==getKategorijaId()){
                            artikli.add(i);
                        }
                    }
                }else{
                    for(Item i:items){
                        if(i.getGradId()==getGradId()){
                            artikli.add(i);
                        }
                    }
                }
                adapterArtikli.setArtikli(artikli);
                if (adapterArtikli.getItemCount() == 0) {
                    imgNoResults.setVisibility(View.VISIBLE);
                    txtNoResults.setVisibility(View.VISIBLE);
                } else {
                    imgNoResults.setVisibility(View.INVISIBLE);
                    txtNoResults.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MutableLiveData<ArrayList<Item>> mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromUser(Utils.getInstance().getKorisnik().getId());
        progressBar.setVisibility(View.VISIBLE);
        mld.observe(this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                if(items==null){
                    return;
                }
                progressBar.setVisibility(View.INVISIBLE);
                ArrayList<Item> artikli = mld.getValue();
                adapterArtikli.setArtikli(artikli);
                recyclerArtikli.setAdapter(adapterArtikli);
            }
        });
    }
}