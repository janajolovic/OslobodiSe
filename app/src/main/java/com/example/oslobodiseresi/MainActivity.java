package com.example.oslobodiseresi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerArtikli;
    private ArrayList<Artikal> artikli = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerArtikli = findViewById(R.id.artikli);
        //artikli
        artikli.add(new Artikal("Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg"));
        artikli.add(new Artikal("Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg"));
        artikli.add(new Artikal("Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg"));
        artikli.add(new Artikal("Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg"));
        //recycler view


        ArtikalAdapter adapterArtikli = new ArtikalAdapter(this);
        adapterArtikli.setArtikli(artikli);

        recyclerArtikli.setAdapter(adapterArtikli);
        recyclerArtikli.setLayoutManager(new GridLayoutManager(this, 2));
    }

}