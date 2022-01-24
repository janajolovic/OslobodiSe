package com.example.oslobodiseresi.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oslobodiseresi.ArtikalAdapter;
import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Kategorija;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends ToolbarNavigacijaSetup {

    private RecyclerView recyclerArtikli;
    private NavigationView navigationView;
    private ProgressBar progress;
    private ImageView imgLogo;
    private ImageView imgNoResults;

    private ArtikalAdapter adapterArtikli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);
        progress = findViewById(R.id.progress);

        recyclerArtikli = findViewById(R.id.artikli);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(true);

        imgLogo = findViewById(R.id.imgLogo);
        imgNoResults = findViewById(R.id.imgNoResults);

        recyclerArtikli.setLayoutManager(new GridLayoutManager(this, 2));

        //recycler view
        adapterArtikli = new ArtikalAdapter(this);

        MutableLiveData<ArrayList<Item>> artikli = UserRepository.getInstance(MainApplication.apiManager).GetAllItems();
        artikli.observe(MainActivity.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                Log.println(Log.ASSERT,"[MainActivity]","Pozvan sam");
                adapterArtikli.setArtikli(artikli.getValue());
                recyclerArtikli.setAdapter(adapterArtikli);
                progress.setVisibility(View.INVISIBLE);
                imgLogo.setVisibility(View.INVISIBLE);
            }
        });

        SearchView searchView = findViewById(R.id.search_bar);
       // searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterArtikli.getFilter().filter(query);
                if (adapterArtikli.getItemCount() == 0) {
                    imgNoResults.setVisibility(View.VISIBLE);
                } else {
                    imgNoResults.setVisibility(View.INVISIBLE);
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void primeniFiltere() {
        super.primeniFiltere();
        MutableLiveData<ArrayList<Item>> mld;
        if (getKategorijaId() != 0 && getGradId() != 0) {
            mld = ItemRepository.getInstance(MainApplication.apiManager).getItemFromKategorijaGrad(
                    getKategorijaId(),
                    getGradId()
            );
        }else if(getKategorijaId() == 0 && getGradId() == 0){
            mld = ItemRepository.getInstance(MainApplication.apiManager).getAllItems();
        }else if(getKategorijaId() != 0){
            mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromKategorija(getKategorijaId());
        }else{
            mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromGrad(getGradId());
        }
        mld.observe(MainActivity.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                adapterArtikli.setArtikli(mld.getValue());
                if (adapterArtikli.getItemCount() == 0) {
                    imgNoResults.setVisibility(View.VISIBLE);
                } else {
                    imgNoResults.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
