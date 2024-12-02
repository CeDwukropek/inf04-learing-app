package com.example.inf04_edu_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {

    private String title;
    private String description;
    private List<Section> sections;

    public Item(String title, String description) {
        this.title = title;
        this.description = description;
        this.sections = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void addSection(SectionType type, String content) {
        sections.add(new Section(type, content));
    }

    public static class Section implements Serializable {
        private SectionType type;
        private String content;

        public Section(SectionType type, String content) {
            this.type = type;
            this.content = content;
        }

        public SectionType getType() {
            return type;
        }

        public String getContent() {
            return content;
        }
    }

    public enum SectionType {
        TEXT, LIST, CODE
    }
}
