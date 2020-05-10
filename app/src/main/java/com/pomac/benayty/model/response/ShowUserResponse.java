package com.pomac.benayty.model.response;

import com.pomac.benayty.model.UserData;

public class ShowUserResponse {

    private int status;

    private UserData data;

    public int getStatus() {
        return status;
    }

    public UserData getData() {
        return data;
    }
}
