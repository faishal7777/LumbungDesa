package com.runupstdio.lumbungdesa.Model;

public class DetilTransaksi {
    String namaProduk, jumlahProduk, hargaProduk, imgProduk;

    public DetilTransaksi(String namaProduk, String jumlahProduk, String hargaProduk, String imgProduk) {
        this.namaProduk = namaProduk;
        this.jumlahProduk = jumlahProduk;
        this.hargaProduk = hargaProduk;
        this.imgProduk = imgProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getJumlahProduk() {
        return jumlahProduk;
    }

    public void setJumlahProduk(String jumlahProduk) {
        this.jumlahProduk = jumlahProduk;
    }

    public String getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(String hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getImgProduk() {
        return imgProduk;
    }

    public void setImgProduk(String imgProduk) {
        this.imgProduk = imgProduk;
    }
}
