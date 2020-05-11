package com.example.myclass.model;

public class dataMatkulModel {
    public String kode;
    public String matkul;
    public String dosen;
    public String hari;

    public dataMatkulModel(String kode, String matkul, String dosen, String hari) {
        this.kode   = kode;
        this.matkul = matkul;
        this.dosen  = dosen;
        this.hari   = hari;
    }

    public String getKode() {
        return kode;
    }

    public String getMatkul() {
        return matkul;
    }

    public String getDosen() {
        return dosen;
    }

    public String getHari() {
        return hari;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setMatkul(String matkul) {
        this.matkul = matkul;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }
}
