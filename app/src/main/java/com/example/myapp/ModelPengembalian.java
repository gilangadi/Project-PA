package com.example.myapp;

public class ModelPengembalian {

    private int id;
    private String judul;
    private String pengarang;
    private String tahun_terbit;
    private String urlimages;
    private String urlpdf;

    public ModelPengembalian(int id, String judul, String pengarang, String tahun_terbit, String urlimages, String urlpdf ) {
        this.id = id;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun_terbit = tahun_terbit;
        this.urlimages = urlimages;
        this.urlpdf = urlpdf;
    }

    public int getId() {
        return id;
    }
    public String getJudulJurnal() {
        return judul;
    }
    public String getPengarangJurnal() {
        return pengarang;
    }
    public String getTahun_TerbitJurnal() {
        return tahun_terbit;
    }
    public String getUrlimagesJurnal() {
        return urlimages;
    }
    public String getUrlpdf() {
        return urlpdf;
    }

}
