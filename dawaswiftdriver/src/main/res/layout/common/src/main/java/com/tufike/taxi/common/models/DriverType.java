package com.tufike.taxi.common.models;

import com.google.gson.annotations.Expose;

public class DriverType {
    @Expose
    Integer id;
    @Expose
    String title;
    @Expose
    String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
