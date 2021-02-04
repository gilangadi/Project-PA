package com.example.myapp;

public class ModelAdminStatusKembali {

    private int id;
    private int id_user;
    private String judul;
    private String pengarang;
    private String tahun_terbit;
    private String urlimages;
    private String urlpdf;
    private String status_buku_jurnal;

    private String nama;
    private String nim;
    private String prodi;
    private String created_at;

    public ModelAdminStatusKembali(int id, int id_user, String judul, String pengarang, String tahun_terbit, String urlimages, String urlpdf,String status_buku_jurnal, String nama,String nim,String prodi,String created_at) {
        this.id = id;
        this.id_user = id_user;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun_terbit = tahun_terbit;
        this.urlimages = urlimages;
        this.urlpdf = urlpdf;
        this.status_buku_jurnal = status_buku_jurnal;
        this.nama = nama;
        this.nim = nim;
        this.prodi = prodi;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }
    public int getId_user() {
        return id_user;
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
        return created_at ;
    }
}
