package com.example.oslobodiseresi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import okhttp3.internal.Util;

public class MojiOglasiActivity extends ToolbarNavigacijaSetup {

    private NavigationView navigationView;
    private RecyclerView recyclerArtikli;
    private FloatingActionButton dodajArtikal;
    private ArtikalAdapter adapterArtikli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_moji_oglasi);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(true);

        recyclerArtikli = findViewById(R.id.mojiArtikli);

        adapterArtikli = new ArtikalAdapter(this);

        recyclerArtikli.setLayoutManager(new GridLayoutManager(this, 2));

        MutableLiveData<ArrayList<Item>> artikli = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromUser(Utils.getInstance().getKorisnik().getId());
        artikli.observe(MojiOglasiActivity.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                adapterArtikli.setArtikli(artikli.getValue());
                recyclerArtikli.setAdapter(adapterArtikli);
            }
        });

        dodajArtikal = findViewById(R.id.dodajArtikal);
        dodajArtikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MojiOglasiActivity.this, NapraviArtikal.class);
                startActivity(intent);
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
        Toast.makeText(this, "MojiOglasi", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void primeniFiltere() {
//        super.primeniFiltere();
//        MutableLiveData<ArrayList<Item>> mld;
//        if (getKategorijaId() != 0 && getGradId() != 0) {
//            mld = ItemRepository.getInstance(MainApplication.apiManager).getItemFromKategorijaGrad(
//                    getKategorijaId(),
//                    getGradId()
//            );
//        }else if(getKategorijaId() == 0 && getGradId() == 0){
//            mld = ItemRepository.getInstance(MainApplication.apiManager).getAllItems();
//        }else if(getKategorijaId() != 0){
//            mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromKategorija(getKategorijaId());
//        }else{
//            mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromGrad(getGradId());
//        }
//        mld.observe(MojiOglasiActivity.this, new Observer<ArrayList<Item>>() {
//            @Override
//            public void onChanged(ArrayList<Item> items) {
//                adapterArtikli.setArtikli(mld.getValue());
//            }
//        });
//    }

}