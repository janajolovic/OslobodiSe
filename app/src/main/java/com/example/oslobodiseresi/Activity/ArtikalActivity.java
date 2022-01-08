package com.example.oslobodiseresi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

public class ArtikalActivity extends ToolbarNavigacijaSetup {

    public static final String ARTIKAL_ID_KEY = "artikalId";
    private TextView txtOpis;
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

        setToolbar(this);

        isFav = false;
        imgFav = findViewById(R.id.imgFav);
        imgFav.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isFav = !isFav;
                if (isFav) {
                    imgFav.setImageResource(R.drawable.ic_heart);
                } else {
                    imgFav.setImageResource(R.drawable.ic_prazno_srce);
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
            if(artikalId != -1){
                Item incomingArtikal = Utils.getInstance().getArtikalById(artikalId);
                if(incomingArtikal!=null){
                    setData(incomingArtikal);
                }
            }
        }
    }

    private void setData(Item artikal) {
        txtOpis.setText(artikal.getOpis());
        txtNaziv.setText(artikal.getNaziv());
//        Glide.with(this)
//                .asBitmap()
//                .load(artikal.getSlika())
//                .into(img);
    }

    private void initViews() {
        txtOpis = findViewById(R.id.opis);
        img = findViewById(R.id.imgOpsirno);
        txtNaziv = findViewById(R.id.txtNaziv);
    }
}