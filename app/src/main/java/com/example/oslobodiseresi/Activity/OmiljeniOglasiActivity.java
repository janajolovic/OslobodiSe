package com.example.oslobodiseresi.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class OmiljeniOglasiActivity extends ToolbarNavigacijaSetup {

    private NavigationView navigationView;
    private RecyclerView recyclerArtikli;
    private ArtikalAdapter adapterArtikli;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_omiljeni_oglasi);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setToolbar(true);

        recyclerArtikli = findViewById(R.id.omiljeniArtikli);
        recyclerArtikli.setLayoutManager(new GridLayoutManager(this, 2));

        //recycler view
        adapterArtikli = new ArtikalAdapter(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        MutableLiveData<ArrayList<Item>> mld = UserRepository.getInstance(MainApplication.apiManager).GetOmiljeniOglasiFromUser(
                Utils.getInstance().getKorisnik().getId()
        );
        mld.observe(this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                progressBar.setVisibility(View.INVISIBLE);
                ArrayList<Item> artikli = mld.getValue();
                adapterArtikli.setArtikli(artikli);
                recyclerArtikli.setAdapter(adapterArtikli);
            }
        });
        SearchView searchView = findViewById(R.id.search_bar);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapterArtikli.getFilter().filter(newText);
                return false;
            }
        });
    }
    @Override
    public void primeniFiltere() {
        super.primeniFiltere();

        MutableLiveData<ArrayList<Item>> mld = UserRepository.getInstance(MainApplication.apiManager).GetOmiljeniOglasiFromUser(Utils.getInstance().getKorisnik().getId());
        mld.observe(OmiljeniOglasiActivity.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
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
            }
        });
    }

    void izbrisiOglas(int id) {
        adapterArtikli.izbrisiArtikal(id);
    }

    int bitniOglasId = -1;

    public void setBitniOglasId(int bitniOglasId) {
        this.bitniOglasId = bitniOglasId;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MutableLiveData<ArrayList<Item>> mld = UserRepository.getInstance(MainApplication.apiManager).GetOmiljeniOglasiFromUser(
                Utils.getInstance().getKorisnik().getId()
        );
        progressBar.setVisibility(View.VISIBLE);
        mld.observe(this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                progressBar.setVisibility(View.INVISIBLE);
                ArrayList<Item> artikli = mld.getValue();
                adapterArtikli.setArtikli(artikli);
                recyclerArtikli.setAdapter(adapterArtikli);
            }
        });
    }
}