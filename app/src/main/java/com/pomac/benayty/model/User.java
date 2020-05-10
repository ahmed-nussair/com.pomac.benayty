package com.pomac.benayty.model;

import com.google.gson.annotations.SerializedName;

public class User {

    private String name;

    private String phone;

    @SerializedName("fcm_token")
    private String fcmToken;

    private String imagePath;

    private int likes;

    private int dislikes;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }
}
