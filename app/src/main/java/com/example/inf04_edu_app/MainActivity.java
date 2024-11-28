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
        itemList.add(new Item("Temat 1", "Opis tematu 1"));
        itemList.add(new Item("Temat 2", "Opis tematu 2"));
        itemList.add(new Item("Temat 3", "Opis tematu 3"));

        adapter = new ItemAdapter(itemList, this::onItemClick);
        recyclerView.setAdapter(adapter);
    }

    private void onItemClick(Item item) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();

        // Przekazywanie listy tekstów
        ArrayList<String> texts = new ArrayList<>();
        texts.add("Pierwszy dodatkowy tekst");
        texts.add("Drugi dodatkowy tekst");

        bundle.putStringArrayList("TEXTS", texts);
        intent.putExtras(bundle);
        startActivity(intent);
    }



}
