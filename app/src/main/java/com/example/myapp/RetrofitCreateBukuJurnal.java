package com.example.myapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCreateBukuJurnal {

    private static final String baseURL = "http://192.168.43.183/apiperpus/public/";
    private static Retrofit retro;

    public static Retrofit koneksiRetrofit(){
        if(retro == null){
            retro = new Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retro;
    }
//    private static RetrofitCreateBukuJurnal mIntance;
//    private Retrofit retrofit;
//
//    private RetrofitCreateBukuJurnal(){
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//    }
//
//    public static synchronized RetrofitCreateBukuJurnal getInstance(){
//        if (mIntance == null){
//            mIntance = new RetrofitCreateBukuJurnal();
//        }
//        return mIntance;
//    }
//    public api getApi(){
//        return retrofit.create(api.class);
//    }
}
