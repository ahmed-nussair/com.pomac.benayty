package com.pomac.benayty.model;

import com.google.gson.annotations.SerializedName;

public class Advertisement {

    private int id;

    private String title;

    private String phone;

    private String description;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("created_at")
    private String createdAt;

    private String imagePath;

    private String area;

    private String city;

    private String main;

    private String secondary;

    private User user;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getMain() {
        return main;
    }

    public String getSecondary() {
        return secondary;
    }

    public User getUser() {
        return user;
    }
}
