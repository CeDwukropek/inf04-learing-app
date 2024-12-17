package com.example.inf04_edu_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Item implements Serializable {
    private String title; // Tytuł elementu
    private String description; // Opis elementu
    private List<Section> sections; // Lista sekcji (tekst, kod, obrazy itp.)

    // Konstruktor
    public Item(String title, String description) {
        this.title = title;
        this.description = description;
        this.sections = new ArrayList<>();
    }

    // Gettery
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Section> getSections() {
        return sections;
    }

    // Dodanie sekcji do elementu
    public void addSection(Section section) {
        sections.add(section);
    }

    // Metoda do debugowania
    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sections=" + sections +
                '}';
    }
}
