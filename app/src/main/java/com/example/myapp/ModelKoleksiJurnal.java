package com.example.myapp;

public class ModelKoleksiJurnal {
    private int id_rak;
    private int id;
    private String judul;
    private String pengarang;
    private String tahun_terbit;
    private String urlimages;
    private String urlpdf;

    public ModelKoleksiJurnal(int id, int id_rak,  String judul, String pengarang, String tahun_terbit, String urlimages, String urlpdf ) {
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
