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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.ItemPostModel;
import com.example.oslobodiseresi.Models.Kategorija;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class NapraviArtikal extends ToolbarNavigacijaSetup {

    private NavigationView navigationView;
    private Button dodajArtikal;
    private Spinner spinnerKategorije;
    private Spinner spinnerGradovi;
    private EditText naziv;
    private EditText opis;
    private ImageView izaberi;
    private ImageView slika;
    private ArrayList<String> kategorije;
    private ArrayList<String> gradovi;

    private Bitmap bitmap;

    int SELECT_PHOTO = 1;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_napravi_artikal);

        spinnerKategorije = findViewById(R.id.spinnerKategorije);

        ArrayList<String> kategorije = new ArrayList<>();

        MutableLiveData<ArrayList<Kategorija>> mldKategorije = ItemRepository.getInstance(MainApplication.apiManager).getAllKategorije();
        mldKategorije.observe(NapraviArtikal.this, new Observer<ArrayList<Kategorija>>() {
            @Override
            public void onChanged(ArrayList<Kategorija> kategorija) {
                for(Kategorija k: mldKategorije.getValue()) {
                    Log.println(Log.ASSERT, "[info]","k je "+k.toString());
                    kategorije.add(k.getNaziv());
                }
            }
        });

        ArrayAdapter<String> kategorijeAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, kategorije);

        spinnerKategorije.setAdapter(kategorijeAdapter);

        spinnerGradovi = findViewById(R.id.spinnerGradovi);
        ArrayList<String> gradovi = new ArrayList<>();

        MutableLiveData<ArrayList<Grad>> mldGradovi = ItemRepository.getInstance(MainApplication.apiManager).getAllGradovi();
        mldGradovi.observe(NapraviArtikal.this, new Observer<ArrayList<Grad>>() {
            @Override
            public void onChanged(ArrayList<Grad> grad) {
                for(Grad g: mldGradovi.getValue()) {
                    gradovi.add(g.getNaziv());
                }
            }
        });

        ArrayAdapter<String> gradoviAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, gradovi);

        spinnerGradovi.setAdapter(gradoviAdapter);

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