package com.example.oslobodiseresi.Retrofit;

import com.example.oslobodiseresi.Models.Grad;
import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.ItemPostModel;
import com.example.oslobodiseresi.Models.Kategorija;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Models.LoginModel;
import com.example.oslobodiseresi.Models.RegistarModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestInterface {
    @POST("users/register")
    Call<Korisnik> register(@Body RegistarModel korisnik);

    @POST("users/login")
    Call<Korisnik> login(@Body LoginModel korisnik);

    @GET("items/all")
    Call<ArrayList<Item>>getAllItems();

    @POST("items/post")
    Call<Item> postItem(@Body ItemPostModel model);

    @GET("items/kategorije/all")
    Call<ArrayList<Kategorija>> getAllKategorije();

    @GET("items/gradovi/all")
    Call<ArrayList<Grad>> getAllGradovi();

    @GET("items/{Id}")
    Call<Item> getItem(@Path("Id") int Id);

    @GET("items/user/{userId}")
    Call<ArrayList<Item>> getItemsFromUser(@Path("userId") String Id);

    @GET("items/grad/{gradId}")
    Call<ArrayList<Item>> getItemsFromGrad(@Path("gradId") int gradId);

    @GET("items/kategorije/{kategorijaId}")
    Call<ArrayList<Item>> getItemsFromKategorija(@Path("kategorijaId") int kategorijaId);

    @HTTP(method = "DELETE", path = "items/delete", hasBody = true)
    Call<String> deleteItem(@Body int Id);

}
