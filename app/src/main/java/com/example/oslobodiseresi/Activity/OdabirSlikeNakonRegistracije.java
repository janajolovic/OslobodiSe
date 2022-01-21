package com.example.oslobodiseresi.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.R;
import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;

import java.io.FileNotFoundException;
import java.io.IOException;

public class OdabirSlikeNakonRegistracije extends ToolbarNavigacijaSetup {

    private Button btnPotvrdite;
    private TextView txtPreskocite;
    private ImageView imgProfil;
    private ImageView imgIzaberi;

    Bitmap bitmap;
    int SELECT_PHOTO = 1;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odabir_slike_nakon_registracije);

        btnPotvrdite = findViewById(R.id.btnPotvrdite);
        txtPreskocite = findViewById(R.id.txtPreskocite);
        imgProfil = findViewById(R.id.imgProfil);
        imgIzaberi = findViewById(R.id.imgIzaberi);

        txtPreskocite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OdabirSlikeNakonRegistracije.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnPotvrdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OdabirSlikeNakonRegistracije.this, "Morate prvo odabrati sliku", Toast.LENGTH_SHORT).show();
            }
        });

        imgIzaberi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                someActivityResultLauncher.launch(intent);

                btnPotvrdite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //todo postavi sliku korisniku
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