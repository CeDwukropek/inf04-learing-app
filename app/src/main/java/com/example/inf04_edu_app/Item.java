package com.example.inf04_edu_app;

import java.io.Serializable;

public class Item implements Serializable {
    private String title;
    private String description;
    private int imageResId;

    public Item(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Item(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }
}
