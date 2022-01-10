package com.example.oslobodiseresi.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.oslobodiseresi.R;

import com.example.oslobodiseresi.ToolbarNavigacijaSetup;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ZaDavuda extends ToolbarNavigacijaSetup {

    Button dugme;
    ImageView slika, slika2;
    ImageView izaberi;

    Bitmap bitmap;
    int SELECT_PHOTO = 1;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_za_davuda);

        dugme = findViewById(R.id.dugme);
        slika = findViewById(R.id.slika);
        slika2 = findViewById(R.id.slika2);
        izaberi = findViewById(R.id.izaberi);

        izaberi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                someActivityResultLauncher.launch(intent);
            }
        });

        //slika2 treba da se postavi nakon sto se dugme pritisne
        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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