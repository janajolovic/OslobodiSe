package com.example.oslobodiseresi.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.oslobodiseresi.MainApplication;
import com.example.oslobodiseresi.Models.UploadImage;
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

    Bitmap photo;
    String filePath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
            slika.setImageBitmap(photo);
            Uri tempUri = getImageUri(getApplicationContext(), photo);
            filePath = getRealPathFromURI(tempUri);
        }
    }

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
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                File file = new File(filePath);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                photo.compress(Bitmap.CompressFormat.JPEG, 0, bos);
//                byte[] bitmapdata = bos.toByteArray();
//                String encoded = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
//                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
//                MultipartBody.Part body = MultipartBody.Part.createFormData("", "image.jpg", requestFile);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //Log.println(Log.ASSERT, "[ZaDavud]", imageString);
                MutableLiveData<String> mld = UserRepository.getInstance(MainApplication.apiManager).PostSlika(new UploadImage(imageString));
                mld.observe(ZaDavuda.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        Toast.makeText(ZaDavuda.this, "Slika sacuvana", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//
//        izaberi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                someActivityResultLauncher.launch(intent);
//            }
//        });
//
//        //slika2 treba da se postavi nakon sto se dugme pritisne
//        dugme.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File f = new File(ZaDavuda.this.getCacheDir(), "nesto");
//                try {
//                    f.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos);
//                byte[] bitmapdata = bos.toByteArray();
//
//                FileOutputStream fos = null;
//                try {
//                    fos = new FileOutputStream(f);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    fos.write(bitmapdata);
//                    fos.flush();
//                    fos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
//                MultipartBody.Part body = MultipartBody.Part.createFormData("upload", f.getName(), reqFile);
//                MutableLiveData<ResponseBody> mld = UserRepository.getInstance(MainApplication.apiManager).PostSlika(body);
//                mld.observe(ZaDavuda.this, new Observer<ResponseBody>() {
//                    @Override
//                    public void onChanged(ResponseBody responseBody) {
//                        Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
//                        slika2.setImageBitmap(bmp);
//                    }
//                });
//            }
//        });
//    }
//
//    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
//    new ActivityResultContracts.StartActivityForResult(),
//    new ActivityResultCallback<ActivityResult>() {
//        @Override
//        public void onActivityResult(ActivityResult result) {
//            if (result.getResultCode() == Activity.RESULT_OK) {
//                // There are no request codes
//                Intent data = result.getData();
//                uri = data.getData();
//                try{
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
//                    slika.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e){
//                    e.printStackTrace();
//                } catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//    });
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
}