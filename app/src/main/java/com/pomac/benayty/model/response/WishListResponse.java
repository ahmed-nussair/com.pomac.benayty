package com.pomac.benayty.model.response;

import com.pomac.benayty.model.WishListItem;

import java.util.List;

public class WishListResponse {

    private int status;

    private List<WishListItem> data;

    public int getStatus() {
        return status;
    }

    public List<WishListItem> getData() {
        return data;
    }
}
