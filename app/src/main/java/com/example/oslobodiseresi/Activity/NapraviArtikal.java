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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.ItemPostModel;
import com.example.oslobodiseresi.Models.Kategorija;
import com.example.oslobodiseresi.Models.UploadImage;
import com.example.oslobodiseresi.Retrofit.ItemRepository;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Utils;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
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
    private TextView txtNemaKategorija, txtNemaGrad;

    private Bitmap bitmap;

    int SELECT_PHOTO = 1;
    Uri uri;

    ArrayAdapter<String> kategorijeAdapter;
    ArrayAdapter<String> gradoviAdapter;

    void initViews(){
        dodajArtikal = findViewById(R.id.dodajArtikal);
        spinnerGradovi = findViewById(R.id.spinnerGradovi2);
        spinnerKategorije = findViewById(R.id.spinnerKategorije2);
        naziv = findViewById(R.id.txtNaziv);
        opis = findViewById(R.id.txtOpis);
        slika = findViewById(R.id.slika);
        izaberi = findViewById(R.id.imgIzaberi);
        txtNemaKategorija = findViewById(R.id.txtNemaKategorija);
        txtNemaGrad = findViewById(R.id.txtNemaGrad);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_napravi_artikal);

        initViews();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(false);

        // spinneri tj dropdown meni za izbor grada i kategorije
        MutableLiveData<ArrayList<Kategorija>> mldKategorije = ItemRepository.getInstance(MainApplication.apiManager).getAllKategorije();
        mldKategorije.observe(NapraviArtikal.this, new Observer<ArrayList<Kategorija>>() {
            @Override
            public void onChanged(ArrayList<Kategorija> kategorija) {
                ArrayList<String> kategorije = new ArrayList<>();
                kategorije.add("Izaberite kategoriju");
                for (Kategorija k : mldKategorije.getValue()) {
                    kategorije.add(k.getNaziv());
                }
                kategorijeAdapter = new ArrayAdapter<>(NapraviArtikal.this, android.R.layout.simple_spinner_dropdown_item, kategorije);
                kategorijeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerKategorije.setAdapter(kategorijeAdapter);
                for(int i=0; i<kategorijeAdapter.getCount(); i++){
                    Log.println(Log.ASSERT,"[Kategorije]",kategorijeAdapter.getItem(i));
                }
            }
        });

        MutableLiveData<ArrayList<Grad>> mldGradovi = ItemRepository.getInstance(MainApplication.apiManager).getAllGradovi();
        mldGradovi.observe(NapraviArtikal.this, new Observer<ArrayList<Grad>>() {
            @Override
            public void onChanged(ArrayList<Grad> grad) {
                ArrayList<String> gradovi = new ArrayList<>();
                gradovi.add("Izaberite grad");
                for (Grad g : mldGradovi.getValue()) {
                    gradovi.add(g.getNaziv());
                }
                gradoviAdapter = new ArrayAdapter<>(NapraviArtikal.this, android.R.layout.simple_spinner_dropdown_item, gradovi);
                gradoviAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerGradovi.setAdapter(gradoviAdapter);
                for(int i=0; i<gradoviAdapter.getCount(); i++){
                    Log.println(Log.ASSERT,"[Gradovi]",gradoviAdapter.getItem(i));
                }
            }
        });

        dodajArtikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NapraviArtikal.this, "Morate izabrati sliku artikla", Toast.LENGTH_SHORT).show();
            }
        });

        izaberi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                someActivityResultLauncher.launch(intent);

                dodajArtikal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        if(spinnerKategorije.getSelectedItemPosition() == 0)
                            txtNemaKategorija.setVisibility(View.VISIBLE);
                        if(spinnerGradovi.getSelectedItemPosition() == 0)
                            txtNemaGrad.setVisibility(View.VISIBLE);

                        if(spinnerKategorije.getSelectedItemPosition() == 0 || spinnerGradovi.getSelectedItemPosition() == 0)
                            return;

                        MutableLiveData<Item> mld = ItemRepository.getInstance(MainApplication.apiManager).postItem(new ItemPostModel(
                                naziv.getText().toString(),
                                opis.getText().toString(),
                                spinnerKategorije.getSelectedItemPosition(), spinnerGradovi.getSelectedItemPosition(),
                                Utils.getInstance().getKorisnik().getId(),
                                imageString
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