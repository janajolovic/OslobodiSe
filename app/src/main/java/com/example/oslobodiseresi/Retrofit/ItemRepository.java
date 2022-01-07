package com.example.oslobodiseresi.Retrofit;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Korisnik;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemRepository {
    private static volatile ItemRepository instance;
    private final ApiManager apiManager;

    private  MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>();

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
}
