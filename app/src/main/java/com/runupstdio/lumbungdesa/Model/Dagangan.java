package com.runupstdio.lumbungdesa.Model;

public class Dagangan {
    String imgProdukDagangan, namaProdukDagangan, hargaProdukDagangan;

    public Dagangan(String imgProdukDagangan, String namaProdukDagangan, String hargaProdukDagangan) {
        this.imgProdukDagangan = imgProdukDagangan;
        this.namaProdukDagangan = namaProdukDagangan;
        this.hargaProdukDagangan = hargaProdukDagangan;
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
