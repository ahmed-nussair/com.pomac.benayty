package com.pomac.benayty.model;

import com.google.gson.annotations.SerializedName;

public class Comment {

    private int id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("advertiser_id")
    private String advertiserId;

    private String type;

    private String comment;

    private User user;

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAdvertiserId() {
        return advertiserId;
    }

    public String getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }
}
