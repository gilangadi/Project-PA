package com.example.myapp;

public class ModelAdminStatusPinjam {

    private int id_user;
    private int id;
    private String judul;
    private String pengarang;
    private String tahun_terbit;
    private String urlimages;
    private String urlpdf;

    private String nama;
    private String nim;
    private String prodi;
    private String created_at;
    private String status_buku_jurnal;

    public ModelAdminStatusPinjam(int id, int id_user, String judul, String pengarang, String tahun_terbit,String urlimages, String urlpdf, String nama, String nim, String prodi, String created_at, String status_buku_jurnal) {
        this.id_user = id_user;
        this.id = id;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun_terbit = tahun_terbit;
        this.urlimages = urlimages;
        this.urlpdf = urlpdf;
        this.nama = nama;
        this.nim = nim;
        this.prodi = prodi;
        this.created_at = created_at;
        this.status_buku_jurnal = status_buku_jurnal;
    }

    public int getId_user() {
        return id_user;
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
    public String getNama() {
        return nama;
    }
    public String getNim() {
        return nim;
    }
    public String getProdi() {
        return prodi;
    }
    public String getCreated_at() {
        return created_at;
    }
    public String getStatus_buku_jurnal() {
        return status_buku_jurnal;
    }
}
