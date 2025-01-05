package com.example.inf04_edu_app;

import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Section implements Serializable {
    public enum Type {
        TEXT,
        CODE,
        IMAGE,
        EXAMPLE,
        LIST,
        H1,
        H2,
        H3,
        H4,
        H5,
        H6
    }

    private List<String> listItems; // Lista dla sekcji typu LIST
    private Type type;
    private String content; // Tekst lub kod
    private Integer imageResId; // Zasób obrazka
    private String exampleType; // Typ przykładu: "ListView", "Spinner", etc.

    public Section(Type type, String content) {
        this.type = type;
        this.content = content;
        if (type == Type.LIST) {
            this.listItems = new ArrayList<>();
        }
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

    // Getter i setter dla listItems
    public List<String> getListItems() {
        return listItems;
    }

    public void setContent(String content) {
        if (type != Type.LIST) {
            this.content = content;
        }
    }

    public void setListItems(List<String> items) {
        if (type == Type.LIST) {
            this.listItems = items;
        } else {
            throw new IllegalStateException("This section is not of type LIST.");
        }
    }

    public void addListItem(String item) {
        if (type == Type.LIST) {
            this.listItems.add(item);
        } else {
            throw new IllegalStateException("Cannot add items to a non-LIST section.");
        }
    }
}
