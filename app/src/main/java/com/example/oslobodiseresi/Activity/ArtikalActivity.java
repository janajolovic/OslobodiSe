package com.example.oslobodiseresi.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oslobodiseresi.ArtikalAdapter;
import com.example.oslobodiseresi.KomentarAdapter;
import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Komentar;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class ArtikalActivity extends ToolbarNavigacijaSetup {

    public static final String ARTIKAL_ID_KEY = "artikalId";
    private TextView txtOpis, txtKategorija, txtGrad, txtKorisnik;
    private ImageView img;
    private TextView txtNaziv;
    private ImageView artikalBack;
    private ImageView imgFav;
    private Boolean isFav;
    private NavigationView navigationView;
    private TextView txtKontakt;
    private Item artikal;
    private ConstraintLayout constraintLayout;
    private ProgressBar progressBar;
    private TextView txtDodajKomentar;
    private ImageView btnDodajKomentar;
    private KomentarAdapter komentarAdapter;
    private RecyclerView komentariRecycler;

    private ArtikalAdapter parent;
    private Integer adapterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_artikal);

        initViews();
        Intent intent = getIntent();

        if(null != intent)
        {
            int artikalId = intent.getIntExtra(ARTIKAL_ID_KEY, -1);
            Log.println(Log.ASSERT,"[tag]","id je "+artikalId);
            adapterId = artikalId;
            if(artikalId != -1){
                MutableLiveData<Item> mld = ItemRepository.getInstance(MainApplication.apiManager).getItem(artikalId);
                mld.observe(ArtikalActivity.this, new Observer<Item>() {
                    @Override
                    public void onChanged(Item item) {
                        if(mld.getValue() != null){
                            setData(mld.getValue());
                            setViews();
                        }
                    }
                });
            }
        }
    }

    private void setData(Item artikal) {
        this.artikal = artikal;
        txtOpis.setText(artikal.getOpis());
        txtNaziv.setText(artikal.getNaziv());
        txtKategorija.setText(artikal.getKategorija().getNaziv());
        txtGrad.setText(artikal.getGrad().getNaziv());
        txtKorisnik.setText(artikal.getUser().getIme());
        txtKontakt.setText(artikal.getUser().getBrojTelefona());
//      txtKontakt.setText(Utils.getInstance().getKorisnik().getBrojTelefona());
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
        txtKontakt = findViewById(R.id.txtKontakt);
        imgFav = findViewById(R.id.imgFav);
        progressBar = findViewById(R.id.progressBar);
        constraintLayout = findViewById(R.id.grupa);
        txtDodajKomentar = findViewById(R.id.txtDodajKomentar);
        btnDodajKomentar = findViewById(R.id.btnDodajKomentar);
        komentariRecycler = findViewById(R.id.komentari);
    }

    private void setViews() {
        progressBar.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(false);

        artikalBack = findViewById(R.id.artikalBack);
        artikalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtikalActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        if(artikal!=null){
            Context context = ArtikalActivity.this;
            txtKorisnik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance().jeUlogovan() && artikal.getUserId().equals(Utils.getInstance().getKorisnik().getId())) {
                        Intent intent = new Intent(context, MojProfilActivity.class);
                        startActivity(intent);
                    } else  {
                        Intent intent = new Intent(context, ProfilKorisnika.class);
                        intent.putExtra("KORISNIK_ID", artikal.getUserId());
                        startActivity(intent);
                    }

                }
            });
            if(Utils.getInstance().jeUlogovan()){
                MutableLiveData<Boolean> mld = UserRepository.getInstance(MainApplication.apiManager).ProveriOmiljeniOglas(Utils.getInstance().getKorisnik().getId(), artikal.getId());
                mld.observe(ArtikalActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        progressBar.setVisibility(View.INVISIBLE);
                        constraintLayout.setVisibility(View.VISIBLE);
                        isFav = aBoolean.booleanValue();
                        if(isFav){
                            imgFav.setImageResource(R.drawable.ic_heart);
                        } else {
                            imgFav.setImageResource(R.drawable.ic_prazno_srce);
                        }
                    }
                });
                imgFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isFav=!isFav;
                        if (isFav) {
                            imgFav.setImageResource(R.drawable.ic_heart);
                            MutableLiveData<String> mld = UserRepository.getInstance(MainApplication.apiManager).DodajOmiljeniOglas(Utils.getInstance().getKorisnik().getId(), artikal.getId());
                            mld.observe((AppCompatActivity)context, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    Toast.makeText(context, "Artikal je dodat u omiljene oglase", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            imgFav.setImageResource(R.drawable.ic_prazno_srce);
                            MutableLiveData<String> mld = UserRepository.getInstance(MainApplication.apiManager).IzbrisiOmiljeniOglas(Utils.getInstance().getKorisnik().getId(), artikal.getId());
                            mld.observe((AppCompatActivity)context, new Observer<String>() {
                                @Override
                                public void onChanged(String s) {
                                    Toast.makeText(context, "Artikal je uklonjen iz omiljenih oglasa", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
            else{
                progressBar.setVisibility(View.INVISIBLE);
                constraintLayout.setVisibility(View.VISIBLE);
                imgFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Morate biti prijavljeni da biste oznacili oglas", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            komentariRecycler.setLayoutManager(new GridLayoutManager(this, 1));

            //recycler view
            komentarAdapter = new KomentarAdapter(this);

            ArrayList<Komentar> komentari = artikal.getKomentari();

            komentarAdapter.setKomentari(komentari);
            komentariRecycler.setAdapter(komentarAdapter);

            btnDodajKomentar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance().jeUlogovan()) {
                        MutableLiveData<String> mld = ItemRepository.getInstance(MainApplication.apiManager).DodajKomentar(artikal.getId(), artikal.getUserId(), txtDodajKomentar.getText().toString());
                        mld.observe(ArtikalActivity.this, new Observer<String>() {
                            @Override
                            public void onChanged(String s) {
                                int index = komentarAdapter.getKomentari().size();
                                //komentarAdapter.getKomentari().add(komentar);
                                //komentarAdapter.notifyItemInserted(index);
                                //todo da se zavrsi
                            }
                        });
                    } else {
                        Toast.makeText(context, "Morate biti prijavljeni da biste objavili komentar", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else{
        }
    }
}