package com.example.oslobodiseresi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

public class ArtikalActivity extends ToolbarNavigacijaSetup {

    public static final String ARTIKAL_ID_KEY = "artikalId";
    private TextView txtOpis, txtKategorija, txtGrad, txtKorisnik;
    private ImageView img;
    private TextView txtNaziv;
    private ImageView artikalBack;
    private ImageView imgFav;
    private Boolean isFav;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_artikal);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(false);

        isFav = false;
        imgFav = findViewById(R.id.imgFav);
        imgFav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(Utils.getInstance().jeUlogovan())
                {
                    isFav = !isFav;
                    if (isFav) {
                        imgFav.setImageResource(R.drawable.ic_heart);
                    } else {
                        imgFav.setImageResource(R.drawable.ic_prazno_srce);
                    }
                }
                else
                {
                    Toast.makeText(ArtikalActivity.this, "Morate biti prijavljeni da biste oznacili oglas", Toast.LENGTH_SHORT).show();
                }
            }
        }) ;

        artikalBack = findViewById(R.id.artikalBack);
        artikalBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                Intent intent = new Intent(ArtikalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        initViews();
        Intent intent = getIntent();

        if(null != intent)
        {
            int artikalId = intent.getIntExtra(ARTIKAL_ID_KEY, -1);
            Log.println(Log.ASSERT,"[tag]","id je "+artikalId);
            if(artikalId != -1){
                MutableLiveData<Item> mld = ItemRepository.getInstance(MainApplication.apiManager).getItem(artikalId);
                mld.observe(ArtikalActivity.this, new Observer<Item>() {
                    @Override
                    public void onChanged(Item item) {
                        if(mld.getValue() != null){
                            setData(mld.getValue());
                        }
                    }
                });
            }
        }
    }

    private void setData(Item artikal) {
        txtOpis.setText(artikal.getOpis());
        txtNaziv.setText(artikal.getNaziv());
        txtKategorija.setText(artikal.getKategorija().getNaziv());
        txtGrad.setText(artikal.getGrad().getNaziv());
        txtKorisnik.setText(artikal.getUser().getIme());
//        Glide.with(this)
//                .asBitmap()
//                .load(artikal.getSlika())
//                .into(img);
    }

    private void initViews() {
        txtOpis = findViewById(R.id.opis);
        img = findViewById(R.id.imgOpsirno);
        txtNaziv = findViewById(R.id.txtNaziv);
        txtKategorija = findViewById(R.id.txtKategorija);
        txtGrad = findViewById(R.id.txtGrad);
        txtKorisnik = findViewById(R.id.txtKorisnik);
    }
}