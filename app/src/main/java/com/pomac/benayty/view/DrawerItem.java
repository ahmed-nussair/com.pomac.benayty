package com.pomac.benayty.view;

public class DrawerItem {

    private String drawerItemTitle;

    private int drawerItemImageResource;

    public DrawerItem(String drawerItemTitle, int drawerItemImageResource) {
        this.drawerItemTitle = drawerItemTitle;
        this.drawerItemImageResource = drawerItemImageResource;
    }

    public String getDrawerItemTitle() {
        return drawerItemTitle;
    }

    public int getDrawerItemImageResource() {
        return drawerItemImageResource;
    }
}
