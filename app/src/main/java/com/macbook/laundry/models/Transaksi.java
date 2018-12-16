package com.macbook.laundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Transaksi {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("administrator")
    @Expose
    private Administrator administrator;
    @SerializedName("cabang")
    @Expose
    private Cabang cabang;
    @SerializedName("kurir")
    @Expose
    private Kurir kurir;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("berat")
    @Expose
    private Double berat;
    @SerializedName("waktuPesan")
    @Expose
    private Long waktuPesan;
    @SerializedName("waktuSelesai")
    @Expose
    private Long waktuSelesai;
    @SerializedName("menuLaundry")
    @Expose
    private List<MenuLaundry> menuLaundry = null;
    @SerializedName("item")
    @Expose
    private List<Item> item = null;
    @SerializedName("status")
    @Expose
    private String status;

    public Transaksi() {
    }

    public Transaksi(Customer customer, String alamat, Double latitude, Double longitude, Long waktuPesan, List<MenuLaundry> menuLaundry, String status) {
        this.customer = customer;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.waktuPesan = waktuPesan;
        this.menuLaundry = menuLaundry;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public Cabang getCabang() {
        return cabang;
    }

    public void setCabang(Cabang cabang) {
        this.cabang = cabang;
    }

    public Kurir getKurir() {
        return kurir;
    }

    public void setKurir(Kurir kurir) {
        this.kurir = kurir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getBerat() {
        return berat;
    }

    public void setBerat(Double berat) {
        this.berat = berat;
    }

    public Long getWaktuPesan() {
        return waktuPesan;
    }

    public void setWaktuPesan(Long waktuPesan) {
        this.waktuPesan = waktuPesan;
    }

    public Long getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(Long waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public List<MenuLaundry> getMenuLaundry() {
        return menuLaundry;
    }

    public void setMenuLaundry(List<MenuLaundry> menuLaundry) {
        this.menuLaundry = menuLaundry;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
