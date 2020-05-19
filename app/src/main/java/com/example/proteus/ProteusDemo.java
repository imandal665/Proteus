package com.example.proteus;

import android.app.Application;

import com.example.proteus.converters.GsonConverterFactory;
import com.example.proteus.managers.ProteusManager;
import com.flipkart.android.proteus.gson.ProteusTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;


public class ProteusDemo extends Application {
    private static final String BASE_URL = "http://139.59.40.185:3000/api/";

    private Gson gson;
    private Retrofit retrofit;
    private ProteusManager proteusManager;

    @Override
    public void onCreate() {
        super.onCreate();

        // register the proteus type adapter to deserialize proteus resources
        ProteusTypeAdapterFactory adapter = new ProteusTypeAdapterFactory(this);
        gson = new GsonBuilder()
                .registerTypeAdapterFactory(adapter)
                .create();

        // add gson to retrofit to allow deserializing proteus resources when fetched via retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();

        // ProteusManager is a reference implementation to fetch and
        // update all proteus resources from a remote server
        proteusManager = new ProteusManager(getRetrofit());
    }

    public Gson getGson() {
        return gson;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public ProteusManager getProteusManager() {
        return proteusManager;
    }
}
