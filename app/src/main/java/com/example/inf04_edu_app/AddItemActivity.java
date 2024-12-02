package com.example.inf04_edu_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    private LinearLayout sectionContainer;
    private EditText titleEditText, descriptionEditText;
    private List<Item.Section> sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        sectionContainer = findViewById(R.id.sectionContainer);
        titleEditText = findViewById(R.id.titleEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);

        sections = new ArrayList<>();

        Button addTextButton = findViewById(R.id.addTextButton);
        Button addListButton = findViewById(R.id.addListButton);
        Button addCodeButton = findViewById(R.id.addCodeButton);
        Button saveButton = findViewById(R.id.saveButton);

        addTextButton.setOnClickListener(v -> addTextSection());
        addListButton.setOnClickListener(v -> addListSection());
        addCodeButton.setOnClickListener(v -> addCodeSection());

        saveButton.setOnClickListener(v -> saveItem());
    }

    private void addTextSection() {
        EditText editText = new EditText(this);
        editText.setHint("Wprowadź tekst");
        sectionContainer.addView(editText);
        sections.add(new Item.Section(Item.SectionType.TEXT, editText));
    }

    private void addListSection() {
        EditText editList = new EditText(this);
        editList.setHint("Wprowadź listę punktów (jeden wiersz = jeden punkt)");
        sectionContainer.addView(editList);
        sections.add(new Item.Section(Item.SectionType.LIST, editList));
    }

    private void addCodeSection() {
        EditText editCode = new EditText(this);
        editCode.setHint("Wprowadź kod");
        sectionContainer.addView(editCode);
        sections.add(new Item.Section(Item.SectionType.CODE, editCode));
    }

    private void saveItem() {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Item newItem = new Item(title, description);
        for (Item.Section section : sections) {
            newItem.addSection(section.getType(), ((EditText) section.getView()).getText().toString());
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_ITEM", newItem);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
