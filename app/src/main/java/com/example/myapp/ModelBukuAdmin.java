package com.example.myapp;

public class ModelBukuAdmin {

    private int id;
    private String judul;
    private String pengarang;
    private String tahun_terbit;
    private String urlimages;
    private String urlpdf;
    private String status_buku_jurnal;

    public ModelBukuAdmin(int id, String judul, String pengarang, String tahun_terbit, String urlimages,String urlpdf,String status_buku_jurnal ) {
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
    public String getJudulJurnalAdmin() {
        return judul;
    }
    public String getPengarangJurnalAdmin() {
        return pengarang;
    }
    public String getTahun_TerbitJurnalAdmin() {
        return tahun_terbit;
    }
    public String getUrlimagesJurnalAdmin() {
        return urlimages;
    }
    public String getUrlpdfAdmin() {
        return urlpdf;
    }
    public String getStatus_buku_jurnal() {
        return status_buku_jurnal;
    }

}
