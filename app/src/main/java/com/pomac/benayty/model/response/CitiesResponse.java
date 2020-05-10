package com.pomac.benayty.model.response;

import com.pomac.benayty.model.City;

import java.util.List;

public class CitiesResponse {

    private int status;

    private List<City> data;

    public int getStatus() {
        return status;
    }

    public List<City> getData() {
        return data;
    }
}
