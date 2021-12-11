package com.example.oslobodiseresi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerArtikli;
    private ArrayList<Artikal> artikli = new ArrayList<>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerArtikli = findViewById(R.id.artikli);
        //artikli
        artikli.add(new Artikal("Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg"));
        artikli.add(new Artikal("Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg"));
        artikli.add(new Artikal("Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg"));
        artikli.add(new Artikal("Kasika", "https://image.shutterstock.com/image-photo/wooden-spoon-placed-on-white-260nw-1716144358.jpg"));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_ikonica:
                Toast.makeText(this, "ikonica", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
