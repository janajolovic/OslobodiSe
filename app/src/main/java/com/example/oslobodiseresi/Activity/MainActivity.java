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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends ToolbarNavigacijaSetup {

    private RecyclerView recyclerArtikli;
    private NavigationView navigationView;
    private ProgressBar progress;
    private Button primeniFiltere;
    Spinner spinnerKategorije, spinnerGradovi;

    int izabraniGrad, izabranaKategorija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        progress = findViewById(R.id.progress);

        primeniFiltere = findViewById(R.id.primeniFiltere);

        recyclerArtikli = findViewById(R.id.artikli);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(true);

        recyclerArtikli.setLayoutManager(new GridLayoutManager(this, 2));

        spinnerKategorije = findViewById(R.id.spinnerKategorije);
        spinnerGradovi = findViewById(R.id.spinnerGradovi);

            MutableLiveData<ArrayList<Kategorija>> mldKategorije = ItemRepository.getInstance(MainApplication.apiManager).getAllKategorije();
            mldKategorije.observe(MainActivity.this, new Observer<ArrayList<Kategorija>>() {
                @Override
                public void onChanged(ArrayList<Kategorija> kategorija) {
                        ArrayList<String> kategorije = new ArrayList<>();
                        kategorije.add("Sve kategorije");
                        for (Kategorija k : mldKategorije.getValue()) {
                            kategorije.add(k.getNaziv());
                        }
                        ArrayAdapter<String> kategorijeAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, kategorije);
                        kategorijeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerKategorije.setAdapter(kategorijeAdapter);
                    }
            });


            MutableLiveData<ArrayList<Grad>> mldGradovi = ItemRepository.getInstance(MainApplication.apiManager).getAllGradovi();
            mldGradovi.observe(MainActivity.this, new Observer<ArrayList<Grad>>() {
                @Override
                public void onChanged(ArrayList<Grad> grad) {
                    ArrayList<String> gradovi = new ArrayList<>();
                    gradovi.add("Svi gradovi");
                    for (Grad g : mldGradovi.getValue()) {
                            gradovi.add(g.getNaziv());
                        }
                        ArrayAdapter<String> gradoviAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, gradovi);
                        gradoviAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerGradovi.setAdapter(gradoviAdapter);
                        //spinnerGradovi.setOnItemSelectedListener(MainActivity.this);
                    }
            });

        //recycler view
        ArtikalAdapter adapterArtikli = new ArtikalAdapter(this);

        MutableLiveData<ArrayList<Item>> artikli = UserRepository.getInstance(MainApplication.apiManager).GetAllItems();
        artikli.observe(MainActivity.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                adapterArtikli.setArtikli(artikli.getValue());
                recyclerArtikli.setAdapter(adapterArtikli);
                progress.setVisibility(View.INVISIBLE);
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

        primeniFiltere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MutableLiveData<ArrayList<Item>> mld;
                if (spinnerGradovi.getSelectedItemPosition() != 0 && spinnerKategorije.getSelectedItemPosition() != 0) {
                    mld = ItemRepository.getInstance(MainApplication.apiManager).getItemFromKategorijaGrad(
                            spinnerKategorije.getSelectedItemPosition(),
                            spinnerGradovi.getSelectedItemPosition()
                    );
                }else if(spinnerKategorije.getSelectedItemPosition() == 0 && spinnerGradovi.getSelectedItemPosition() == 0){
                    mld = ItemRepository.getInstance(MainApplication.apiManager).getAllItems();
                }else if(spinnerKategorije.getSelectedItemPosition() != 0){
                    mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromKategorija(spinnerKategorije.getSelectedItemPosition());
                }else{
                    mld = ItemRepository.getInstance(MainApplication.apiManager).getItemsFromGrad(spinnerGradovi.getSelectedItemPosition());
                }
                mld.observe(MainActivity.this, new Observer<ArrayList<Item>>() {
                    @Override
                    public void onChanged(ArrayList<Item> items) {
                        adapterArtikli.setArtikli(mld.getValue());
                    }
                });
            }
        });
    }
}
