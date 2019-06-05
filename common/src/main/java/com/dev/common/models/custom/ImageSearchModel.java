package com.dev.common.models.custom;

import ir.mirrajabi.searchdialog.core.Searchable;

public class ImageSearchModel implements Searchable {
    private String mName;
    private String mImageUrl;

    public ImageSearchModel(String name, String imageUrl) {
        mName = name;
        mImageUrl = imageUrl;
    }

    @Override
    public String getTitle() {
        return mName;
    }

    public String getName() {
        return mName;
    }

    public ImageSearchModel setName(String name) {
        mName = name;
        return this;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public ImageSearchModel setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
        return this;
    }
}