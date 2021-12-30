package com.example.oslobodiseresi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public static RestInterface Create(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RestInterface.class);
    }
}
