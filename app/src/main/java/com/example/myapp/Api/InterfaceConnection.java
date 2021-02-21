package com.example.myapp.Api;

import com.example.myapp.Model.Data_Response;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceConnection {

    @FormUrlEncoded

    @POST("rak")
    Call<Data_Response> create(

            @Field("judul") String judul,
            @Field("pengarang") String pengarang,
            @Field("tahun_terbit") String tahun_terbit,
            @Field("status_buku_jurnal") String status_buku_jurnal,
            @Field("status") String status

    );
}
