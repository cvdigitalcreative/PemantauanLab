package com.rosalina.pemantauanlab;

import com.google.firebase.database.PropertyName;

public class Model {
    public String nama;
    public String kelas;
    public String nama_barang;
    public String no_unit;
    public String lokasi;
    public String jumlah;
    public String uraian_kerusakan;

//    public Model(String nama, String kelas, String nama_barang,
//                 String no_unit, String lokasi, String jumlah, String uraian_kerusakan){
//        this.nama = nama;
//        this.kelas = kelas;
//        this.nama_barang = nama_barang;
//        this.no_unit = no_unit;
//        this.lokasi = lokasi;
//        this.jumlah = jumlah;
//        this.uraian_kerusakan = uraian_kerusakan;
//    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getNo_unit() {
        return no_unit;
    }

    public void setNo_unit(String no_unit) {
        this.no_unit = no_unit;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getUraian_kerusakan() {
        return uraian_kerusakan;
    }

    public void setUraian_kerusakan(String uraian_kerusakan) {
        this.uraian_kerusakan = uraian_kerusakan;
    }
}
