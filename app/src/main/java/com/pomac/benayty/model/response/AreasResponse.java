package com.pomac.benayty.model.response;

import com.pomac.benayty.model.Area;

import java.util.List;

public class AreasResponse {

    private int status;

    private List<Area> data;

    public int getStatus() {
        return status;
    }

    public List<Area> getData() {
        return data;
    }
}
