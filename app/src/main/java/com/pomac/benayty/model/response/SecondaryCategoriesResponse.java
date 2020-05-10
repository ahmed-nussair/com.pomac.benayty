package com.pomac.benayty.model.response;

import com.pomac.benayty.model.SecondaryCategory;

import java.util.List;

public class SecondaryCategoriesResponse {

    private int status;

    private List<SecondaryCategory> data;

    public int getStatus() {
        return status;
    }

    public List<SecondaryCategory> getData() {
        return data;
    }
}
