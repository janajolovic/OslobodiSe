package com.example.oslobodiseresi.Activity;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.oslobodiseresi.ArtikalAdapter;
import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends ToolbarNavigacijaSetup {

    private RecyclerView recyclerArtikli;
    private ArrayList<Item> artikli = new ArrayList<>();
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_main);

        recyclerArtikli = findViewById(R.id.artikli);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(this);

        recyclerArtikli.setLayoutManager(new GridLayoutManager(this, 2));

        //recycler view
        ArtikalAdapter adapterArtikli = new ArtikalAdapter(this);

        MutableLiveData<ArrayList<Item>> artikli = UserRepository.getInstance(MainApplication.apiManager).GetAllItems();
        artikli.observe(MainActivity.this, new Observer<ArrayList<Item>>() {
            @Override
            public void onChanged(ArrayList<Item> items) {
                adapterArtikli.setArtikli(artikli.getValue());
                recyclerArtikli.setAdapter(adapterArtikli);
            }
        });


    }
}
