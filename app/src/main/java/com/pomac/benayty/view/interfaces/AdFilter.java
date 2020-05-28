package com.pomac.benayty.view.interfaces;

public interface AdFilter {

    void setMainCategory(int mainCategoryId, String mainCategoryName);

    void setSecondaryCategory(int secondaryCategoryId, String secondaryCategoryName);

    void setArea(int areaId, String areaName);

    void setCity(int cityId, String cityName);

    int getMainCategoryId();

    int getAreaId();
}
