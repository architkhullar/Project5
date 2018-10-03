package com.example.sunidhi.inclass04;

import com.google.gson.annotations.SerializedName;

public class ProductRequest {
    @SerializedName("region")
    String region;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
