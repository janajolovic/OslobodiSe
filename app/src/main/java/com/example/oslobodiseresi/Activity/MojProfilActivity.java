package com.example.oslobodiseresi.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;

import okhttp3.ResponseBody;

public class MojProfilActivity extends ToolbarNavigacijaSetup {

    private TextView ime;
    private TextView email;
    private TextView kontakt;
    private TextView ocena;
    private NavigationView navigationView;
    private ImageView imgProfil;
    private boolean promenjen = false;
    private Bitmap bitmap;

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
        imgProfil = findViewById(R.id.mojaSlika);

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
        }

        setToolbar(false);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MutableLiveData<ResponseBody> mld = UserRepository.getInstance(MainApplication.apiManager).GetProfilna(Utils.getInstance().getKorisnik().getId());
        mld.observe(MojProfilActivity.this, new Observer<ResponseBody>() {
            @Override
            public void onChanged(ResponseBody responseBody) {
                if(!promenjen){

                    Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());

                    if(bmp!=null){
                        promenjen=true;
                        bitmap=bmp;
                        imgProfil.setImageBitmap(bitmap);
                        Log.println(Log.ASSERT,"nbtn","bitmapa je promenjena");
                    }
                }
//                try {
//                    Log.println(Log.ASSERT, "[response]", responseBody.string().substring(0,100));
//                } catch (IOException e) {
//                    Log.println(Log.ASSERT, "[response err]", e.getMessage());
//                }
//
                Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                imgProfil.setImageBitmap(bmp);
//                try {
//                    responseBody.byteStream().reset();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                if (bmp != null)
//                else {
//                    Log.println(Log.ASSERT, "[bmp]", "null");
//                }
            }
        });
    }
}