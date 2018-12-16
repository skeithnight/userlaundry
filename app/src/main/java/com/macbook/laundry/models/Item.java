package com.macbook.laundry.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("status")
    @Expose
    private String status;
}
