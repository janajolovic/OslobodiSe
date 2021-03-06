package com.example.oslobodiseresi.Retrofit;

import static android.util.Log.ASSERT;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Models.LoginModel;
import com.example.oslobodiseresi.Models.RegistarModel;
import com.example.oslobodiseresi.Models.UploadImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static volatile UserRepository instance;
    private final ApiManager apiManager;

    private final MutableLiveData<Korisnik> korisnik = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>();
    private final MutableLiveData<String> str = new MutableLiveData<>();
    private final MutableLiveData<String> str64 = new MutableLiveData<>();
    private final MutableLiveData<Boolean> bool = new MutableLiveData<>();
    private final MutableLiveData<ResponseBody> responseBody = new MutableLiveData<>();
    private final MutableLiveData<Float> flt = new MutableLiveData<>();
    private final MutableLiveData<Bitmap> bitmap = new MutableLiveData<>();

    private UserRepository(ApiManager apiManager){
        this.apiManager = apiManager;
    }

    public static UserRepository getInstance(ApiManager apiManager){
        if(instance == null){
            instance = new UserRepository(apiManager);
        }
        return instance;
    }

    public MutableLiveData<Korisnik> Register(RegistarModel model){
        apiManager.RegisterUser(model, new Callback<Korisnik>() {
            @Override
            public void onResponse(Call<Korisnik> call, Response<Korisnik> response) {
                if(response.isSuccessful()){
                    korisnik.setValue(response.body());
                }else{
                    korisnik.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<Korisnik> call, Throwable t) {
                korisnik.setValue(null);
            }
        });
        return korisnik;
    }

    public MutableLiveData<Korisnik> Login(LoginModel model) {
        apiManager.LoginUser(model, new Callback<Korisnik>() {
            @Override
            public void onResponse(Call<Korisnik> call, Response<Korisnik> response) {
                if (response.isSuccessful()) {
                    korisnik.setValue(response.body());
                }else{
                    korisnik.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<Korisnik> call, Throwable t) {
                korisnik.setValue(null);
            }
        });
        return korisnik;
    }


    public MutableLiveData<ArrayList<Item>> GetAllItems() {
        apiManager.GetAllItems(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                if (response.isSuccessful()) {
                    items.setValue(response.body());
                }else{
                    items.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                items.setValue(null);
            }
        });
        return items;
    }

    public MutableLiveData<ArrayList<Item>> GetOmiljeniOglasiFromUser(String UserId){
        apiManager.GetOmiljeniOglasiFromUser(UserId, new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                if(response.isSuccessful()){
                    items.setValue(response.body());
                }else{
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
            }
        });
        return items;
    }

    public MutableLiveData<String> DodajOmiljeniOglas(String UserId, int OglasId){
        apiManager.DodajOmiljeniOglas(UserId, OglasId, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str.setValue(response.body());
                } else {
                    str.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                str.setValue(t.getMessage());
            }
        });
        return  str;
    }

    public MutableLiveData<String> IzbrisiOmiljeniOglas(String UserId, int OglasId){
        apiManager.IzbrisiOmiljeniOglas(UserId, OglasId, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str.setValue(response.body());
                }else {
                    str.setValue(response.message());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                str.setValue(t.getMessage());
            }
        });
        return  str;
    }

    public MutableLiveData<Korisnik> GetKorisnikById(String Id){
        apiManager.GetKorisnikById(Id, new Callback<Korisnik>() {
            @Override
            public void onResponse(Call<Korisnik> call, Response<Korisnik> response) {
                if(response.isSuccessful()){
                    korisnik.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Korisnik> call, Throwable t) {

            }
        });

        return korisnik;
    }

    public MutableLiveData<Boolean> ProveriOmiljeniOglas(String UserId, int OglasId){
        apiManager.ProveriOmiljeniOglas(UserId, OglasId, new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    bool.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

        return bool;
    }

    public MutableLiveData<Float> DodajOcenu(String UserId, String RaterId, float ocena){
        apiManager.DodajOcenu(UserId, RaterId, ocena, new Callback<Float>() {
            @Override
            public void onResponse(Call<Float> call, Response<Float> response) {
                if(response.isSuccessful()){
                    flt.setValue(response.body());
                }else{
                }
            }

            @Override
            public void onFailure(Call<Float> call, Throwable t) {
            }
        });
        return flt;
    }

    public MutableLiveData<String> LajkujKomentar(int KomentarId, String UserId){
        apiManager.LajkujKomentar(KomentarId, UserId, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str.setValue(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
        return str;
    }

    public MutableLiveData<String> PostSlika(UploadImage slika){
        apiManager.PostSlika(slika, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str.setValue(response.body());
                }else{
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
        return str;
    }

    public MutableLiveData<String> GetProfilna(String UserId){
        apiManager.GetProfilna(UserId, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str64.setValue(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
        return str64;
    }

    public MutableLiveData<String> PostaviProfilnu(String UserId, UploadImage imageDetails){
        apiManager.PostaviProfilnu(UserId, imageDetails, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str.setValue(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

        return str;
    }

    public MutableLiveData<String> PostaviSlikuArtikla(String ItemId, UploadImage imageDetails){
        apiManager.PostaviProfilnu(ItemId, imageDetails, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str.setValue(response.body());
                } else {
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });

        return str;
    }
}
