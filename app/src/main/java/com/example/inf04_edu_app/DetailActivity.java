package com.example.inf04_edu_app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        LinearLayout layout = findViewById(R.id.dynamicContentLayout);

        // Pobieranie przekazanego obiektu Item
        Item itemDetail = (Item) getIntent().getSerializableExtra("ITEM");


        Log.d("SECTIONS", "Liczba sekcji: " + itemDetail.getSections().size());


        if (itemDetail != null) {
            // Wyświetlanie tytułu
            TextView titleTextView = new TextView(this);
            titleTextView.setText(itemDetail.getTitle());
            titleTextView.setTextSize(24);
            titleTextView.setTypeface(null, Typeface.BOLD);
            layout.addView(titleTextView);

            // Wyświetlanie opisu
            TextView descriptionTextView = new TextView(this);
            descriptionTextView.setText(itemDetail.getDescription());
            descriptionTextView.setTextSize(18);
            descriptionTextView.setPadding(0, 16, 0, 16);
            layout.addView(descriptionTextView);

            // Wyświetlanie sekcji
            for (Section section : itemDetail.getSections()) {
                switch (section.getType()) {
                    case TEXT:
                        TextView textView = new TextView(this);
                        String content = section.getContent();
                        if (content != null) {
                            textView.setText(content);
                        } else {
                            textView.setText("Brak treści"); // Tekst zastępczy
                        }
                        layout.addView(textView);
                        break;

                    case CODE:
                        TextView codeView = new TextView(this);
                        String codeContent = section.getContent();
                        if (codeContent != null) {
                            SpannableString code = new SpannableString(codeContent);
                            applySyntaxHighlighting(code);
                            codeView.setText(code);
                        } else {
                            codeView.setText("Kod niedostępny");
                        }
                        codeView.setBackgroundColor(Color.parseColor("#0A0A0A"));
                        layout.addView(codeView);
                        break;

                    case IMAGE:
                        ImageView imageView = new ImageView(this);
                        if (section.getImageResId() != null) {
                            imageView.setImageResource(section.getImageResId());
                        }
                        layout.addView(imageView);
                        break;

                    case EXAMPLE:
                        View exampleView = createExampleView(section.getExampleType());
                        layout.addView(exampleView);
                        break;
                }
            }

        }

        // Przycisk powrotu do MainActivity
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private View createExampleView(String exampleType) {
        switch (exampleType) {
            case "ListView":
                ListView listView = new ListView(this);
                String[] listData = {"Element 1", "Element 2", "Element 3"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
                listView.setAdapter(adapter);
                return listView;

            case "Spinner":
                Spinner spinner = new Spinner(this);
                String[] spinnerData = {"Opcja 1", "Opcja 2", "Opcja 3"};
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerData);
                spinner.setAdapter(spinnerAdapter);
                return spinner;

            default:
                TextView errorView = new TextView(this);
                errorView.setText("Nieznany przykład: " + exampleType);
                return errorView;
        }
    }


    private void addTextSection(LinearLayout layout, String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(16);
        textView.setPadding(0, 8, 0, 8);
        layout.addView(textView);
    }

    private void addCodeSection(LinearLayout layout, String codeSnippet) {
        TextView codeTextView = new TextView(this);

        // Ustawienia wyglądu dla sekcji kodu
        SpannableString spannableCode = new SpannableString(codeSnippet);
        applySyntaxHighlighting(spannableCode);

        codeTextView.setText(spannableCode);
        codeTextView.setTextSize(14);
        codeTextView.setTypeface(Typeface.MONOSPACE);
        codeTextView.setBackgroundColor(Color.parseColor("#0A0A0A"));
        codeTextView.setTextColor(Color.WHITE);
        codeTextView.setPadding(16, 16, 16, 16);
        codeTextView.setMovementMethod(new ScrollingMovementMethod());

        // Dodanie obramowania dla sekcji kodu
        codeTextView.setBackgroundResource(R.drawable.code_background);

        layout.addView(codeTextView);

        // Przycisk kopiowania kodu
        Button copyButton = new Button(this);
        copyButton.setText("Kopiuj kod");
        copyButton.setOnClickListener(v -> copyToClipboard(codeSnippet));
        layout.addView(copyButton);
    }

    private void addBulletListSection(LinearLayout layout, String[] bulletPoints) {
        for (String point : bulletPoints) {
            TextView bulletPointView = new TextView(this);
            bulletPointView.setText("• " + point);
            bulletPointView.setTextSize(16);
            bulletPointView.setPadding(0, 4, 0, 4);
            layout.addView(bulletPointView);
        }
    }

    private void applySyntaxHighlighting(SpannableString code) {
        // Kolorowanie słów kluczowych
        String[] keywords = {"public", "void", "class", "int", "String", "new", "return"};
        for (String keyword : keywords) {
            highlightPattern(code, "\\b" + keyword + "\\b", Color.BLUE);
        }

        // Kolorowanie komentarzy
        highlightPattern(code, "//.*", Color.GREEN);

        // Kolorowanie ciągów znaków
        highlightPattern(code, "\".*?\"", Color.MAGENTA);
    }

    private void highlightPattern(SpannableString spannable, String pattern, int color) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(spannable);
        while (matcher.find()) {
            spannable.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Code Snippet", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Kod skopiowany do schowka!", Toast.LENGTH_SHORT).show();
    }
}
