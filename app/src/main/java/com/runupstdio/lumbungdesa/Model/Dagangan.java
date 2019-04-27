package com.runupstdio.lumbungdesa.Model;

public class Dagangan {
    String imgProdukDagangan, namaProdukDagangan, hargaProdukDagangan;
    private int prodid;

    public Dagangan(int prodid, String imgProdukDagangan, String namaProdukDagangan, String hargaProdukDagangan) {
        this.imgProdukDagangan = imgProdukDagangan;
        this.namaProdukDagangan = namaProdukDagangan;
        this.hargaProdukDagangan = hargaProdukDagangan;
        this.prodid = prodid;
    }

    public int getProdid() {
        return prodid;
    }

    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    public String getImgProdukDagangan() {
        return imgProdukDagangan;
    }

    public void setImgProdukDagangan(String imgProdukDagangan) {
        this.imgProdukDagangan = imgProdukDagangan;
    }

    public String getNamaProdukDagangan() {
        return namaProdukDagangan;
    }

    public void setNamaProdukDagangan(String namaProdukDagangan) {
        this.namaProdukDagangan = namaProdukDagangan;
    }

    public String getHargaProdukDagangan() {
        return hargaProdukDagangan;
    }

    public void setHargaProdukDagangan(String hargaProdukDagangan) {
        this.hargaProdukDagangan = hargaProdukDagangan;
    }
}
