package com.pomac.benayty.model.response;

import com.pomac.benayty.model.Advertisement;

import java.util.List;

public class UserAdsResponse {

    private int status;

    private List<Advertisement> data;

    public int getStatus() {
        return status;
    }

    public List<Advertisement> getData() {
        return data;
    }
}
