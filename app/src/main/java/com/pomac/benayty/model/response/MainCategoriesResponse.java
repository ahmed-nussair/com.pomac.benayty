package com.pomac.benayty.model.response;

import com.pomac.benayty.model.MainCategory;

import java.util.List;

public class MainCategoriesResponse {

    private int status;

    private List<MainCategory> data;

    public int getStatus() {
        return status;
    }

    public List<MainCategory> getData() {
        return data;
    }
}
