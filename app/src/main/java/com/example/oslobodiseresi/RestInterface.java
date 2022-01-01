package com.example.oslobodiseresi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestInterface {
    @POST("users/register")
    Call<Korisnik> register(@Body RegistarModel korisnik);

    @POST("users/login")
    Call<Korisnik> login(@Body LoginModel korisnik);
}
