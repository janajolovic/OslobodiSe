package com.example.oslobodiseresi.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.UploadImage;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

public class MojProfilActivity extends ToolbarNavigacijaSetup {

    private TextView ime;
    private TextView email;
    private TextView kontakt;
    private TextView ocena;
    private ImageView promeniSliku;
    private NavigationView navigationView;
    private ImageView imgProfil;
    private Bitmap bitmap;
    private Bitmap bitmapProfilna;
    Uri uri;

    Button btnPotvrdite;
    Button btnOtkazite;

    private boolean izBaze = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_moj_profil);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setToolbar(false);

        ime = findViewById(R.id.ime);
        email = findViewById(R.id.email);
        kontakt = findViewById(R.id.kontakt);
        ocena = findViewById(R.id.ocena);
        imgProfil = findViewById(R.id.mojaSlika);
        promeniSliku = findViewById(R.id.imgPromeniSliku);

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

            MutableLiveData<Bitmap> mld = UserRepository.getInstance(MainApplication.apiManager).GetProfilna(Utils.getInstance().getKorisnik().getId());
            mld.observe(MojProfilActivity.this, new Observer<Bitmap>() {
                    @Override
                    public void onChanged(Bitmap bitmap) {
                        bitmapProfilna = bitmap;
                        imgProfil.setImageBitmap(bitmap);
                    }
                });

            promeniSliku.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    izBaze = false;

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    someActivityResultLauncher.launch(intent);

                    btnPotvrdite = findViewById(R.id.btnPotvrdite);
                    btnOtkazite = findViewById(R.id.btnOtkazite);
                    btnPotvrdite.setVisibility(View.VISIBLE);
                    btnOtkazite.setVisibility(View.VISIBLE);
                    btnPotvrdite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] imageBytes = baos.toByteArray();
                            String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                            UserRepository.getInstance(MainApplication.apiManager).PostSlika(new UploadImage(imageString));
                            btnPotvrdite.setVisibility(View.GONE);
                            btnOtkazite.setVisibility(View.GONE);
                            bitmapProfilna = bitmap;
                        }
                    });
                    btnOtkazite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnPotvrdite.setVisibility(View.GONE);
                            btnOtkazite.setVisibility(View.GONE);
                            imgProfil.setImageBitmap(bitmapProfilna);
                        }
                    });
                }
            });
        }

    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        uri = data.getData();
                        try{
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            imgProfil.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e){
                            e.printStackTrace();
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            });
}