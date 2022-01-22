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
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.R;

import com.example.oslobodiseresi.Retrofit.UserRepository;
import com.example.oslobodiseresi.ToolbarNavigacijaSetup;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ZaDavuda extends ToolbarNavigacijaSetup {
    private NavigationView navigationView;
    Button dugme;
    ImageView slika, slika2;
    ImageView izaberi;

    Bitmap bitmap;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_activity_za_davuda);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setToolbar(false);

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
                File f = new File(ZaDavuda.this.getCacheDir(), "");
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
                MultipartBody.Part body = MultipartBody.Part.createFormData("upload", f.getName(), reqFile);

                MutableLiveData<ResponseBody> mld = UserRepository.getInstance(MainApplication.apiManager).PostSlika(body);
                mld.observe(ZaDavuda.this, new Observer<ResponseBody>() {
                    @Override
                    public void onChanged(ResponseBody responseBody) {

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