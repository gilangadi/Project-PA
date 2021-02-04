package com.example.myapp;

public class User {

    private int id;
    private String nama, nim, prodi, status;

    public User(int id, String nama, String nim, String prodi, String status) {
        this.id = id;
        this.nama = nama;
        this.nim = nim;
        this.prodi = prodi;
        this.status = status;
    }

    public int getId() {
        return id;
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
    public String getStatus() {
        return status;
    }
}
