package com.example.oslobodiseresi.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.ItemPostModel;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NapraviArtikal extends ToolbarNavigacijaSetup {

    private NavigationView navigationView;
    private Button dodajArtikal;

    private EditText naziv;
    private EditText opis;
    private ImageView izaberi;
    private ImageView slika;

    private Bitmap bitmap;

    int SELECT_PHOTO = 1;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_napravi_artikal);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(this);

        naziv = findViewById(R.id.txtNaziv);
        opis = findViewById(R.id.txtOpis);
        slika = findViewById(R.id.slika);

        izaberi = findViewById(R.id.imgIzaberi);
        izaberi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                someActivityResultLauncher.launch(intent);
            }
        });

        dodajArtikal = findViewById(R.id.dodajArtikal);
        dodajArtikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MutableLiveData<Item> mld = ItemRepository.getInstance(MainApplication.apiManager).postItem(new ItemPostModel(
                        naziv.getText().toString(),
                        opis.getText().toString(),
                        1,1,
                        Utils.getInstance().getKorisnik().getId()
                ));
                mld.observe(NapraviArtikal.this, new Observer<Item>() {
                    @Override
                    public void onChanged(Item item) {
                        Intent intent2 = new Intent(NapraviArtikal.this, MainActivity.class);
                        startActivity(intent2);
                    }
                });
            }
        });
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
                    slika.setImageBitmap(bitmap);
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