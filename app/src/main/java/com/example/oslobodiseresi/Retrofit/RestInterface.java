package com.example.oslobodiseresi.Retrofit;

import com.example.oslobodiseresi.Models.Item;
import com.example.oslobodiseresi.Models.Korisnik;
import com.example.oslobodiseresi.Models.LoginModel;
import com.example.oslobodiseresi.Models.RegistarModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestInterface {
    @POST("users/register")
    Call<Korisnik> register(@Body RegistarModel korisnik);

    @POST("users/login")
    Call<Korisnik> login(@Body LoginModel korisnik);

    @GET("items/all")
    Call<ArrayList<Item>>getAllItems();
}
