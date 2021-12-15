package com.example.oslobodiseresi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ArtikalActivity extends AppCompatActivity {

    public static final String ARTIKAL_ID_KEY = "artikalId";
    private TextView txtOpis;
    private ImageView img;
    private TextView txtNaziv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikal);

        initViews();

        Intent intent = getIntent();

        if(null != intent)
        {
            int artikalId = intent.getIntExtra(ARTIKAL_ID_KEY, -1);
            if(artikalId != -1){
                Artikal incomingArtikal = Utils.getInstance().getArtikalById(artikalId);
                if(incomingArtikal!=null){
                    setData(incomingArtikal);
                }
            }
        }
    }

    private void setData(Artikal artikal) {
        txtOpis.setText(artikal.getOpis());
        txtNaziv.setText(artikal.getNaziv());
        Glide.with(this)
                .asBitmap()
                .load(artikal.getUrlSlike())
                .into(img);
    }

    private void initViews() {
        txtOpis = findViewById(R.id.opis);
        img = findViewById(R.id.imgOpsirno);
        txtNaziv = findViewById(R.id.txtNaziv);
    }
}