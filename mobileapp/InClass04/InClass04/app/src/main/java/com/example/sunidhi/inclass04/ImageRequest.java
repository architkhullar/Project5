package com.example.sunidhi.inclass04;

import com.google.gson.annotations.SerializedName;

public class ImageRequest {
    @SerializedName("photo")
    String photo;

    public String getphoto() {
        return photo;
    }

    public void setphoto(String photo) {
        this.photo = photo;
    }
}
