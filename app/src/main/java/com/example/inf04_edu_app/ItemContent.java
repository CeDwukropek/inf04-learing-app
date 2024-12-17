package com.example.inf04_edu_app;

import java.io.Serializable;
import java.util.List;

public class ItemContent implements Serializable {
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_LIST = 1;
    public static final int TYPE_CODE = 2;

    private int contentType;
    private String text;
    private List<String> listItems;
    private String codeSnippet;

    public ItemContent(int contentType, String text) {
        this.contentType = contentType;
        this.text = text;
    }

    public ItemContent(int contentType, List<String> listItems) {
        this.contentType = contentType;
        this.listItems = listItems;
    }

    public ItemContent(int contentType, String codeSnippet, boolean isCode) {
        this.contentType = TYPE_CODE;
        this.codeSnippet = codeSnippet;
    }

    public int getContentType() {
        return contentType;
    }

    public String getText() {
        return text;
    }

    public List<String> getListItems() {
        return listItems;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }
}

