package com.example.inf04_edu_app;

import android.view.View;

import java.io.Serializable;

public class Section implements Serializable {
    public enum Type {
        TEXT, CODE, IMAGE, EXAMPLE
    }

    private Type type;
    private String content; // Tekst lub kod
    private Integer imageResId; // Zasób obrazka
    private String exampleType; // Typ przykładu: "ListView", "Spinner", etc.

    public Section(Type type, String content, Integer imageResId) {
        this.type = type;
        this.content = content;
        this.imageResId = imageResId;
    }

    public Section(Type type, String exampleType) {
        this.type = type;
        this.exampleType = exampleType;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public Integer getImageResId() {
        return imageResId;
    }

    public String getExampleType() {
        return exampleType;
    }
}
