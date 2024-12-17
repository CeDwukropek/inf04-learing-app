package com.example.inf04_edu_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;
    private static final int CREATE_ITEM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();
        loadSampleItems();

        adapter = new ItemAdapter(itemList, this::onItemClick);
        recyclerView.setAdapter(adapter);
    }

    private void loadSampleItems() {
        // Przykładowe elementy\
        Item item1 = new Item("ListView", "Opis ListView w Androidzie");
        item1.addSection(new Section(Section.Type.TEXT, "ListView to widok służący do wyświetlania listy elementów.", null));
        item1.addSection(new Section(Section.Type.CODE,
                "                ListView listView = new ListView(this);\n" +
                "                String[] listData = {\"Element 1\", \"Element 2\", \"Element 3\"};\n" +
                "                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);\n" +
                "                listView.setAdapter(adapter);"
                , null));
        item1.addSection(new Section(Section.Type.EXAMPLE, "ListView"));

        Item item2 = new Item("Spinner", "Opis Spinnera (ComboBox) w Androidzie.");
        item2.addSection(new Section(Section.Type.TEXT, "Spinner pozwala użytkownikowi wybrać jedną opcję z listy.", null));
        item2.addSection(new Section(Section.Type.CODE,
                "                Spinner spinner = new Spinner(this);\n" +
                "                String[] spinnerData = {\"Opcja 1\", \"Opcja 2\", \"Opcja 3\"};\n" +
                "                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerData);\n" +
                "                spinner.setAdapter(spinnerAdapter);"
                , null));
        item2.addSection(new Section(Section.Type.EXAMPLE, "Spinner"));

        itemList.add(item1);
        itemList.add(item2);
    }

    private void onItemClick(Item item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ITEM", item);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_ITEM_REQUEST && resultCode == RESULT_OK && data != null) {
            Item newItem = (Item) data.getSerializableExtra("NEW_ITEM");
            if (newItem != null) {
                itemList.add(newItem);
                adapter.notifyItemInserted(itemList.size() - 1);
            }
        }
    }
}
