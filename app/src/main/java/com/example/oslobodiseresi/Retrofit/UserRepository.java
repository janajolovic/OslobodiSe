package com.example.oslobodiseresi.Retrofit;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Models.LoginModel;
import com.example.oslobodiseresi.Models.PrijavljenKorisnikModel;
import com.example.oslobodiseresi.Models.RegistarModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static volatile UserRepository instance;
    private final ApiManager apiManager;

    private final MutableLiveData<Korisnik> korisnik = new MutableLiveData<>();
    private final MutableLiveData<PrijavljenKorisnikModel> prijavljen_korisnik = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>();
    private final MutableLiveData<String> str = new MutableLiveData<>();

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
                    Log.println(Log.ERROR, "[Greska]",response.message());
                    korisnik.setValue(null);
                }

            }

            @Override
            public void onFailure(Call<Korisnik> call, Throwable t) {
                Log.println(Log.ERROR, "[Greska]",t.getMessage());
                korisnik.setValue(null);
            }
        });
        return korisnik;
    }

    public MutableLiveData<PrijavljenKorisnikModel> Login(LoginModel model) {
        apiManager.LoginUser(model, new Callback<PrijavljenKorisnikModel>() {
            @Override
            public void onResponse(Call<PrijavljenKorisnikModel> call, Response<PrijavljenKorisnikModel> response) {
                if (response.isSuccessful()) {
                    prijavljen_korisnik.setValue(response.body());
                }else{
                    Log.println(Log.ERROR, "[Greska]",response.message());
                    prijavljen_korisnik.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<PrijavljenKorisnikModel> call, Throwable t) {
                Log.println(Log.ERROR, "[Greska]",t.getMessage());
                prijavljen_korisnik.setValue(null);
            }
        });
        return prijavljen_korisnik;
    }


    public MutableLiveData<ArrayList<Item>> GetAllItems() {
        apiManager.GetAllItems(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                if (response.isSuccessful()) {
                    items.setValue(response.body());
                }else{
                    Log.println(Log.ERROR, "[Greska]",response.message());
                    items.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                Log.println(Log.ERROR, "[Greska]",t.getMessage());
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
                    Log.println(Log.ASSERT, "[Omiljeni OnResponse]", response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                Log.println(Log.ASSERT, "[Omiljeni OnFailure]", t.getMessage());
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
}
