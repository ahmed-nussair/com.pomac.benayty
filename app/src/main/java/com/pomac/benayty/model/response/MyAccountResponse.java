package com.pomac.benayty.model.response;

import com.google.gson.annotations.SerializedName;
import com.pomac.benayty.model.User;

public class MyAccountResponse {

    private int status;

    @SerializedName("user_data")
    private User userData;

    public int getStatus() {
        return status;
    }

    public User getUserData() {
        return userData;
    }
}
