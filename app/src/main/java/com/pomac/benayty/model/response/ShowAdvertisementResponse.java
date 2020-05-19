package com.pomac.benayty.model.response;

import com.pomac.benayty.model.Advertisement;

public class ShowAdvertisementResponse {

    private int status;

    private Advertisement data;

    public int getStatus() {
        return status;
    }

    public Advertisement getData() {
        return data;
    }
}
