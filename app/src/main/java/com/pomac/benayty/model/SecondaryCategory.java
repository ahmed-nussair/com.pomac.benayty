package com.pomac.benayty.model;

import com.google.gson.annotations.SerializedName;

public class SecondaryCategory {

    private int id;

    private String name;

    private String imagePath;

    @SerializedName("main_specialist_id")
    private String mainSpecialistId;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getMainSpecialistId() {
        return mainSpecialistId;
    }
}
