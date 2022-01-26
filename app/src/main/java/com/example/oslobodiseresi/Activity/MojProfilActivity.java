package com.example.oslobodiseresi.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

import okhttp3.ResponseBody;

public class MojProfilActivity extends ToolbarNavigacijaSetup {

    private TextView ime;
    private TextView email;
    private TextView kontakt;
    private TextView ocena;
    private NavigationView navigationView;
    private ImageView imgProfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_moj_profil);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ime = findViewById(R.id.ime);
        email = findViewById(R.id.email);
        kontakt = findViewById(R.id.kontakt);
        ocena = findViewById(R.id.ocena);
        imgProfil = findViewById(R.id.imageView)

        if(Utils.getInstance().jeUlogovan())
        {
            ime.setText(Utils.getInstance().getKorisnik().getIme());
            email.setText(Utils.getInstance().getKorisnik().getEmail());
            kontakt.setText(Utils.getInstance().getKorisnik().getBrojTelefona());
            if (Utils.getInstance().getKorisnik().getBrojOcena() == 0) {
                ocena.setText("/");
            } else {
                ocena.setText(String.valueOf(Utils.getInstance().getKorisnik().getZbirOcena() / Utils.getInstance().getKorisnik().getBrojOcena()));
            }
            MutableLiveData<ResponseBody> mld = UserRepository.getInstance(MainApplication.apiManager).GetProfilna(Utils.getInstance().getKorisnik().getId());
            mld.observe(MojProfilActivity.this, new Observer<ResponseBody>() {
                @Override
                public void onChanged(ResponseBody responseBody) {
                    Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                    imgProfil.setImageBitmap(bmp);
                }
            });

        }

        setToolbar(false);
    }

}