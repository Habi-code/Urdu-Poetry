package com.Habib.habibapp.APIs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClients {
    public static Retrofit RETROFIT = null;

    public static Retrofit getClients() {
        if (RETROFIT==null){
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            Gson gson = new GsonBuilder().create();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl("http://192.168.43.147/PoetryApp/")
                    .client(okHttpClient).
                    addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return RETROFIT;
    }
}
