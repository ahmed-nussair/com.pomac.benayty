package com.pomac.benayty.model.response;

import com.google.gson.annotations.SerializedName;
import com.pomac.benayty.model.User;

public class LoginResponse {

    private int status;

    @SerializedName("user_data")
    private User userData;

    private String token;

    private String[] errors;

    public int getStatus() {
        return status;
    }

    public User getUserData() {
        return userData;
    }

    public String getToken() {
        return token;
    }

    public String[] getErrors() {
        return errors;
    }
}
