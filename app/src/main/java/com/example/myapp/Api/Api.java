package com.example.myapp.Api;

import com.example.myapp.Model.ResponseModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    String BASE_URL = "http://192.168.43.183/apiperpus/public/api/";

    @Multipart
    @POST("rak")
    Call<ResponseModel> upload(
//            @Part("urlimages\"; filename=\"image.jpg\" ") RequestBody file,
//            @Part("urlpdf\"; filename=\"image.jpg\" ") RequestBody filepdf,
            @Part("judul") RequestBody judul,
            @Part("pengarang") RequestBody pengarang,
            @Part("tahun_terbit") RequestBody tahun_terbit,
            @Part("status") RequestBody status,
            @Part("status_buku_jurnal") RequestBody status_buku_jurnal
    );
}
