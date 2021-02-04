package com.example.myapp;

public class ModelJurnal {

    private int id;
    private String judul;
    private String pengarang;
    private String tahun_terbit;
    private String urlimages;
    private String urlpdf;
    private String status_buku_jurnal;

    public ModelJurnal(int id, String judul, String pengarang, String tahun_terbit, String urlimages,String urlpdf,String status_buku_jurnal ) {
        this.id = id;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun_terbit = tahun_terbit;
        this.urlimages = urlimages;
        this.urlpdf = urlpdf;
        this.status_buku_jurnal = status_buku_jurnal;
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
    public String getStatus_buku_jurnal() {
        return status_buku_jurnal;
    }

}
