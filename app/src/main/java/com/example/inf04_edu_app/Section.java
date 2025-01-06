package com.example.inf04_edu_app;

import android.util.Log;
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

    private List<String> listItems = new ArrayList<>(); // Lista dla sekcji typu LIST
    private int listId; // id dla listy
    private Type type;
    private String content; // Tekst lub kod

    public Section(Type type, String content) {
        this.type = type;
        this.content = content;
        if (type == Type.LIST) {
            this.listItems = new ArrayList<>();
        }
        this.listId = 0;
    }

    public Section(Type type, String content, int listId) {
        this.type = type;
        this.content = content;
        if (type == Type.LIST) {
            this.listItems = new ArrayList<>();
        }
        this.listId = listId;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getListId() {
        return listId;
    }

    public List<String> getListItems() {
        return listItems;
    }

    public void setListItems(List<String> items) {
        if (type == Type.LIST) {
            this.listItems = items;
            Log.d("addList", "added list");
        } else {
            throw new IllegalStateException("This section is not of type LIST.");
        }
    }
}
