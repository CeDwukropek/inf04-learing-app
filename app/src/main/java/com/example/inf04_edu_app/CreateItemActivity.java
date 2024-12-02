package com.example.inf04_edu_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CreateItemActivity extends AppCompatActivity {

    private EditText titleInput, descriptionInput;
    private LinearLayout sectionsLayout;
    private List<Item.Section> sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        titleInput = findViewById(R.id.editTextTitle);
        descriptionInput = findViewById(R.id.editTextDescription);
        sectionsLayout = findViewById(R.id.sectionsLayout);
        sections = new ArrayList<>();

        Button addTextSectionButton = findViewById(R.id.buttonAddTextSection);
        Button addListSectionButton = findViewById(R.id.buttonAddListSection);
        Button addCodeSectionButton = findViewById(R.id.buttonAddCodeSection);
        Button saveButton = findViewById(R.id.buttonSaveItem);

        addTextSectionButton.setOnClickListener(v -> addSection(Item.SectionType.TEXT));
        addListSectionButton.setOnClickListener(v -> addSection(Item.SectionType.LIST));
        addCodeSectionButton.setOnClickListener(v -> addSection(Item.SectionType.CODE));

        saveButton.setOnClickListener(v -> saveItem());
    }

    private void addSection(Item.SectionType type) {
        EditText newSectionInput = new EditText(this);
        newSectionInput.setHint("Wprowadź treść sekcji (" + type.name() + ")");
        sectionsLayout.addView(newSectionInput);

        sections.add(new Item.Section(type, ""));
        newSectionInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(android.text.Editable s) {
                int index = sectionsLayout.indexOfChild(newSectionInput);
                sections.get(index).setContent(s.toString());
            }
        });
    }

    private void saveItem() {
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Uzupełnij tytuł i opis!", Toast.LENGTH_SHORT).show();
            return;
        }

        Item newItem = new Item(title, description);
        for (Item.Section section : sections) {
            newItem.addSection(section.getType(), section.getContent());
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("NEW_ITEM", newItem);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
