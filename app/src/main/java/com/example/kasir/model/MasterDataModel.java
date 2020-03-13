package com.example.kasir.model;

public class MasterDataModel {

    private int imgResource;
    private String title;
    private String desc;
    private int katId;
    private int idProduk;
    private String namaProduk;
    private String kode;
    private String hargaJual;
    private String produk_gambar;
    private int pcs;
    private int total = 0;

    public MasterDataModel(int imgResource, String title, String desc, int katId, int idProduk, String namaProduk, String produk_gambar, String kode,
                            String hargaJual, int pcs) {
        this.imgResource = imgResource;
        this.title = title;
        this.desc = desc;
        this.katId = katId;
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.kode = kode;
        this.hargaJual = hargaJual;
        this.pcs = pcs;
        this.produk_gambar = produk_gambar;
    }


    public String getProduk_gambar() {
        return produk_gambar;
    }

    public void setProduk_gambar(String produk_gambar) {
        this.produk_gambar = produk_gambar;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIdProduk() {
        return idProduk;
    }

    public void setIdProduk(int idProduk) {
        this.idProduk = idProduk;
    }

    public int getImgResource() {
        return imgResource;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getKatId() {
        return katId;
    }

    public void setKatId(int katId) {
        this.katId = katId;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
    }

    public int getPcs() {
        return pcs;
    }

    public void setPcs(int pcs) {
        this.pcs = pcs;
    }
}
