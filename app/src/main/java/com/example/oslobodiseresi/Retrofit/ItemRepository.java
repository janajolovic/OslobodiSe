package com.example.oslobodiseresi.Retrofit;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.ItemPostModel;
import com.example.oslobodiseresi.Models.Kategorija;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {
    private static volatile ItemRepository instance;
    private final ApiManager apiManager;

    private MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>();
    private MutableLiveData<Item> item = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Kategorija>> kategorije = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Grad>> gradovi = new MutableLiveData<>();
    private MutableLiveData<String> str = new MutableLiveData<>();

    private ItemRepository(ApiManager apiManager){
        this.apiManager = apiManager;
    }

    public static ItemRepository getInstance(ApiManager apiManager){
        if(instance == null){
            instance = new ItemRepository(apiManager);
        }
        return instance;
    }

    public MutableLiveData<ArrayList<Item>> getAllItems(){
        apiManager.GetAllItems(new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                if(response.isSuccessful()){
                    items.setValue(response.body());
                }else{
                    Log.println(Log.ERROR, "[Greska2]",response.message());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {
                Log.println(Log.ERROR, "[Greska3]",t.getMessage());
            }
        });
        return items;
    }

    public MutableLiveData<Item> postItem(ItemPostModel model){
        apiManager.PostItem(model, new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if(response.isSuccessful()){
                    item.setValue(response.body());
                }
                Log.println(Log.ASSERT, "[Post OnResponse]", response.message());
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.println(Log.ASSERT, "[Post OnFailure]", t.getMessage());
            }
        });
        return item;
    }

    public MutableLiveData<ArrayList<Kategorija>> getAllKategorije(){
        apiManager.GetAllKategorije(new Callback<ArrayList<Kategorija>>() {
            @Override
            public void onResponse(Call<ArrayList<Kategorija>> call, Response<ArrayList<Kategorija>> response) {
                if(response.isSuccessful()){
                    kategorije.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Kategorija>> call, Throwable t) {

            }
        });
        return kategorije;
    }

    public MutableLiveData<Item> getItem(int Id){
        apiManager.GetItem(Id, new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if(response.isSuccessful()){
                    item.setValue(response.body());
                    Log.println(Log.ASSERT, "[Greska3]",response.body().toString());
                }else{
                    Log.println(Log.ASSERT, "[Greska5]",response.message());
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Log.println(Log.ASSERT, "[Greska5]",t.getMessage());
            }
        });
        return item;
    }

    public MutableLiveData<ArrayList<Grad>> getAllGradovi(){
        apiManager.GetAllGradovi(new Callback<ArrayList<Grad>>() {
            @Override
            public void onResponse(Call<ArrayList<Grad>> call, Response<ArrayList<Grad>> response) {
                if(response.isSuccessful()){
                    gradovi.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Grad>> call, Throwable t) {

            }
        });
        return gradovi;
    }

    public MutableLiveData<ArrayList<Item>> getItemsFromUser(String Id){
        apiManager.GetItemsFromUser(Id, new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                if(response.isSuccessful()){
                    items.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {

            }
        });
        return items;
    }

    public MutableLiveData<String> DeleteItem(int Id){
        apiManager.DeleteItem(Id, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str.setValue(response.body());
                }
                Log.println(Log.ASSERT, "[Brisanje OnResponse]", response.message());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.println(Log.ASSERT, "[Brisanje OnFailure]", t.getMessage());
            }
        });

        return str;
    }

    public MutableLiveData<ArrayList<Item>> getItemsFromGrad(int Id){
        apiManager.GetItemsFromGrad(Id, new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                if(response.isSuccessful()){
                    items.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {

            }
        });
        return items;
    }

    public MutableLiveData<ArrayList<Item>> getItemsFromKategorija(int Id){
        apiManager.GetItemsFromKategorija(Id, new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                if(response.isSuccessful()){
                    items.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {

            }
        });
        return items;
    }

    public MutableLiveData<ArrayList<Item>> getItemFromKategorijaGrad(int kategorijaId, int gradId){
        apiManager.GetItemsFromKategorijaGrad(kategorijaId, gradId, new Callback<ArrayList<Item>>() {
            @Override
            public void onResponse(Call<ArrayList<Item>> call, Response<ArrayList<Item>> response) {
                if(response.isSuccessful()){
                    items.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Item>> call, Throwable t) {

            }
        });
        return items;
    }

    public MutableLiveData<String> DodajKomentar(int OglasId, String UserId, String tektst){
        apiManager.DodajKomentar(OglasId, UserId, tektst, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return str;
    }

    public MutableLiveData<String> IzbrisiKomentar(int KomentarId){
        apiManager.IzbrisiKomentar(KomentarId, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    str.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return  str;
    }


}
