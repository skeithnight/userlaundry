package com.macbook.laundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuLaundry {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("satuan")
    @Expose
    private String satuan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

}