package com.adafruit.bluefruit.le.connect.app;

public class Item {
    private Integer mImageUrl;
    private String mTitle;
    private String mDescription;
    
    public Item(Integer imageUrl, String title, String description) {
        super();
        mImageUrl = imageUrl;
        mTitle = title;
        mDescription = description;
    }

    public Integer getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(Integer imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }
}
