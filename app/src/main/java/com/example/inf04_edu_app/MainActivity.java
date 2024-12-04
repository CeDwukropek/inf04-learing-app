package com.example.inf04_edu_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inf04_edu_app.DetailActivity;
import com.example.inf04_edu_app.Item;
import com.example.inf04_edu_app.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = new ArrayList<>();

        Item item1 = new Item("ListView", "Tworzenie i użycie list");
        item1.addContent(new ItemContent(ItemContent.TYPE_LIST, List.of("Deklaracja", "Definicja", "Tworzenie listy z wartościami", "Dodanie adaptera")));
        item1.addContent(new ItemContent(ItemContent.TYPE_CODE, "    @Override\n" +
                "    protected void onCreate(Bundle savedInstanceState) {\n" +
                "        /* base code here*/\n" +
                "\n" +
                "        animalsList = findViewById(R.id.animals);\n" +
                "        String [] listToShow = new String []{\"Pies\",\"Kot\",\"Świnka Morska\"} ;\n" +
                "        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, listToShow);\n" +
                "        animalsList.setAdapter(adapter);\n" +
                "    }", true));

        Item item2 = new Item("Temat 2", "Opis tematu 2");
        item2.addContent(new ItemContent(ItemContent.TYPE_TEXT, "Wstęp do tematu 2"));
        item2.addContent(new ItemContent(ItemContent.TYPE_TEXT, "Rozwinięcie tematu 2"));
        item2.addContent(new ItemContent(ItemContent.TYPE_CODE, "public void method2() {\n    System.out.println(\"Kod 2\");\n}", true));
        item2.addContent(new ItemContent(ItemContent.TYPE_LIST, List.of("Punkt 1", "Punkt 2", "Punkt 3")));
        item2.addContent(new ItemContent(ItemContent.TYPE_CODE, "public void method2() {\n    System.out.println(\"Kod 2.4\");\n}", true));

        itemList.add(item1);
        itemList.add(item2);

        adapter = new ItemAdapter(itemList, this::onItemClick);
        recyclerView.setAdapter(adapter);
    }


    private void onItemClick(Item item) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ITEM_DETAIL", item);
        startActivity(intent);
    }
}
