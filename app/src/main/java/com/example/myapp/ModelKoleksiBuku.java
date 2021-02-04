package com.example.myapp;

public class ModelKoleksiBuku {
    private int id_rak;
    private int id;
    private String judul;
    private String pengarang;
    private String tahun_terbit;
    private String urlimages;
    private String urlpdf;

    public ModelKoleksiBuku(int id,int id_rak, String judul, String pengarang, String tahun_terbit, String urlimages, String urlpdf ) {
        this.id_rak = id_rak;
        this.id = id;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun_terbit = tahun_terbit;
        this.urlimages = urlimages;
        this.urlpdf = urlpdf;
    }

    public int getId_rak() {
        return id_rak;
    }
    public int getId() {
        return id;
    }
    public String getJudulBuku() {
        return judul;
    }
    public String getPengarangBuku() {
        return pengarang;
    }
    public String getTahun_TerbitBuku() {
        return tahun_terbit;
    }
    public String getUrlimagesBuku() {
        return urlimages;
    }
    public String getUrlpdf() {
        return urlpdf;
    }

}
