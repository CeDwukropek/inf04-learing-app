package com.example.inf04_edu_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private String title;
    private String description;
    private List<String> textSections;
    private List<Integer> imageResIds; // ID zasobów obrazów
    private List<String> bulletPoints; // Opcjonalne listy punktowane

    public Item(String title, String description) {
        this.title = title;
        this.description = description;
        this.textSections = new ArrayList<>();
        this.imageResIds = new ArrayList<>();
        this.bulletPoints = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTextSections() {
        return textSections;
    }

    public List<Integer> getImageResIds() {
        return imageResIds;
    }

    public List<String> getBulletPoints() {
        return bulletPoints;
    }

    public void addTextSection(String text) {
        textSections.add(text);
    }

    public void addImageResId(int imageResId) {
        imageResIds.add(imageResId);
    }

    public void addBulletPoint(String point) {
        bulletPoints.add(point);
    }
}
