package com.rosalina.pemantauanlab.Model;

import com.google.firebase.database.PropertyName;

public class Model {
    public String terima;
    public String uid;
    public String nama;
    public String kelas;
    public String nama_barang;
    public String no_unit;
    public String lokasi;
    public String jumlah;
    public String uraian_kerusakan;
    public String status_laporan;
    public String status_ongoing;
    public String status_done;
    public String username;
    public String status_read;
    public String status_pelapor;

    public String getStatus_read() {
        return status_read;
    }

    public void setStatus_read(String status_read) {
        this.status_read = status_read;
    }

    public String status;

    public String getStatus_pelapor() {
        return status_pelapor;
    }

    public void setStatus_pelapor(String status_pelapor) {
        this.status_pelapor = status_pelapor;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTerima() {
        return terima;
    }

    public void setTerima(String terima) {
        this.terima = terima;
    }

    public String getStatus_ongoing() {
        return status_ongoing;
    }

    public void setStatus_ongoing(String status_ongoing) {
        this.status_ongoing = status_ongoing;
    }

    public String getStatus_done() {
        return status_done;
    }

    public void setStatus_done(String status_done) {
        this.status_done = status_done;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus_laporan() {
        return status_laporan;
    }

    public void setStatus_laporan(String status_laporan) {
        this.status_laporan = status_laporan;
    }

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
