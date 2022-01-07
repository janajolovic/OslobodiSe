package com.example.oslobodiseresi.Retrofit;

import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Models.LoginModel;
import com.example.oslobodiseresi.Models.RegistarModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class ApiManager {
    private static RestInterface service;
    private static ApiManager apiManager;

    private ApiManager(){
        service = RetrofitService.Create();
    }
    public static ApiManager getInstance(){
        if(apiManager == null) {
            apiManager = new ApiManager();
        }
        return  apiManager;
    }

    public void RegisterUser(RegistarModel model, Callback<Korisnik> callback){
        Call<Korisnik> registerCall = service.register(model);
        registerCall.enqueue(callback);
    }

    public void LoginUser(LoginModel model, Callback<Korisnik> callback) {
        Call<Korisnik> loginCall = service.login(model);
        loginCall.enqueue(callback);
    }

    public void GetAllItems(Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> allItems = service.getAllItems();
        allItems.enqueue(callback);
    }
}