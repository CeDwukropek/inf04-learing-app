package com.example.inf04_edu_app;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class InputDialog {

    public static void show(Context context, String title, Consumer<String> onResult) {
        EditText input = new EditText(context);
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String text = input.getText().toString().trim();
                    if (!text.isEmpty()) {
                        onResult.accept(text);
                    }
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }

    public static void showList(Context context, Consumer<List<String>> onResult) {
        EditText input = new EditText(context);
        input.setHint("Podaj elementy listy oddzielone przecinkami");
        new AlertDialog.Builder(context)
                .setTitle("Dodaj listę")
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String[] items = input.getText().toString().split(",");
                    List<String> list = new ArrayList<>();
                    for (String item : items) {
                        list.add(item.trim());
                    }
                    onResult.accept(list);
                })
                .setNegativeButton("Anuluj", null)
                .show();
    }
}
