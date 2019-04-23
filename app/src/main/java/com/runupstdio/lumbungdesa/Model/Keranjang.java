package com.runupstdio.lumbungdesa.Model;

public class Keranjang {

    String  nama_Produk_Keranjang,
            harga_Produk_Keranjang,
            kuantitas_produk_Keranjang,
            img_Url_Keranjang;
    int iProductId;

    public Keranjang(int productId, String nama_Produk_Keranjang, String harga_Produk_Keranjang, String kuantitas_produk_Keranjang, String img_Url_Keranjang) {
        this.iProductId = productId;
        this.nama_Produk_Keranjang = nama_Produk_Keranjang;
        this.harga_Produk_Keranjang = harga_Produk_Keranjang;
        this.kuantitas_produk_Keranjang = kuantitas_produk_Keranjang;
        this.img_Url_Keranjang = img_Url_Keranjang;
    }

    public int getiProductId() {
        return iProductId;
    }

    public void setiProductId(int productId) {
        this.iProductId = productId;
    }

    public String getNama_Produk_Keranjang() {
        return nama_Produk_Keranjang;
    }

    public void setNama_Produk_Keranjang(String nama_Produk_Keranjang) {
        this.nama_Produk_Keranjang = nama_Produk_Keranjang;
    }

    public String getHarga_Produk_Keranjang() {
        return harga_Produk_Keranjang;
    }

    public void setHarga_Produk_Keranjang(String harga_Produk_Keranjang) {
        this.harga_Produk_Keranjang = harga_Produk_Keranjang;
    }

    public String getKuantitas_produk_Keranjang() {
        return kuantitas_produk_Keranjang;
    }

    public void setKuantitas_produk_Keranjang(String kuantitas_produk_Keranjang) {
        this.kuantitas_produk_Keranjang = kuantitas_produk_Keranjang;
    }

    public String getImg_Url_Keranjang() {
        return img_Url_Keranjang;
    }

    public void setImg_Url_Keranjang(String img_Url_Keranjang) {
        this.img_Url_Keranjang = img_Url_Keranjang;
    }
}
