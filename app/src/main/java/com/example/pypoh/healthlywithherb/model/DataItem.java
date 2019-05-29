package com.example.pypoh.healthlywithherb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataItem {

    @SerializedName("UID")
    @Expose
    private String UID;
    @SerializedName("Nama")
    @Expose
    private String Nama;
    @SerializedName("Penyakit")
    @Expose
    private String Penyakit;
    @SerializedName("Deskripsi")
    @Expose
    private String Deskripsi;
    @SerializedName("Kategori")
    @Expose
    private String Kategori;
    @SerializedName("Gambar")
    @Expose
    private String Gambar;

    public DataItem(String UID, String nama, String penyakit, String deskripsi, String kategori, String gambar) {
        this.UID = UID;
        Nama = nama;
        Penyakit = penyakit;
        Deskripsi = deskripsi;
        Kategori = kategori;
        Gambar = gambar;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public String getPenyakit() {
        return Penyakit;
    }

    public void setPenyakit(String penyakit) {
        Penyakit = penyakit;
    }
}
