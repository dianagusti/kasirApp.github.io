package com.example.kasir.model;

public class MasterDataModel {

    private int imgResource;
    private String title;
    private String desc;

    public MasterDataModel(int imgResource, String title, String desc) {
        this.imgResource = imgResource;
        this.title = title;
        this.desc = desc;
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
}
