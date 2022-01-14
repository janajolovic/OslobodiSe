package com.example.oslobodiseresi.Retrofit;

import android.os.RemoteCallbackList;

import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.ItemPostModel;
import com.example.oslobodiseresi.Models.Kategorija;
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

    public void PostItem(ItemPostModel model, Callback<Item> callback){
        Call<Item> postItem = service.postItem(model);
        postItem.enqueue(callback);
    }

    public void GetAllKategorije(Callback<ArrayList<Kategorija>> callback){
        Call<ArrayList<Kategorija>> getAllKategorije = service.getAllKategorije();
        getAllKategorije.enqueue(callback);
    }

    public void GetAllGradovi(Callback<ArrayList<Grad>> callback){
        Call<ArrayList<Grad>> getAllGradovi = service.getAllGradovi();
        getAllGradovi.enqueue(callback);
    }

    public void GetItem(int Id, Callback<Item> callback){
        Call<Item> getItem = service.getItem(Id);
        getItem.enqueue(callback);
    }

    public void GetItemsFromUser(String Id, Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> getItemsFromUser = service.getItemsFromUser(Id);
        getItemsFromUser.enqueue(callback);
    }

    public void GetItemsFromGrad(int Id, Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> getItemsFromGrad = service.getItemsFromGrad(Id);
        getItemsFromGrad.enqueue(callback);
    }

    public void GetItemsFromKategorija(int Id, Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> getItemsFromKategorija = service.getItemsFromKategorija(Id);
        getItemsFromKategorija.enqueue(callback);
    }

    public void GetItemsFromKategorijaGrad(int kategorijaId, int gradId,  Callback<ArrayList<Item>> callback){
        Call<ArrayList<Item>> getItemsFromKategorijaGrad = service.getItemsFromKategorijaGrad(kategorijaId, gradId);
        getItemsFromKategorijaGrad.enqueue(callback);
    }

    public void DeleteItem(int Id, Callback<String> callback){
        Call<String> deleteItem = service.deleteItem(Id);
        deleteItem.enqueue(callback);
    }
}
