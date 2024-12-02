package com.example.inf04_edu_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private final String title;
    private final String description;
    private List<ItemContent> contents;

    public Item(String title, String description) {
        this.title = title;
        this.description = description;
        this.contents = new ArrayList<>();
    }

    public List<ItemContent> getContents() {
        return contents;
    }

    public void addContent(ItemContent content) {
        contents.add(content);
    }

    public String getTitle() {
        return title;
    }
}