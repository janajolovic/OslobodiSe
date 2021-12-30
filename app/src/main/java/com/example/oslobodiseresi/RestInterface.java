package com.example.oslobodiseresi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestInterface {
    @POST("register")
    Call<Korisnik> register(@Body RegistarModel korisnik);
}
