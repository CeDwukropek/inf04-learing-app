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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
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
            titleTextView.setTextSize(36);
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
                        // Stwórz poziomy scroll view
                        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
                        horizontalScrollView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, // Szerokość na cały ekran
                                LinearLayout.LayoutParams.WRAP_CONTENT // Wysokość dopasowana do zawartości
                        ));

                        // Stwórz TextView dla kodu
                        TextView codeTextView = new TextView(this);
                        SpannableString highlightedCode = highlightCode(section.getContent());
                        codeTextView.setText(highlightedCode);
                        codeTextView.setTextSize(14);
                        codeTextView.setBackgroundColor(Color.parseColor("#0A0A0A")); // Tło czarne
                        codeTextView.setTextColor(Color.WHITE); // Tekst biały
                        codeTextView.setPadding(16, 16, 16, 16); // Marginesy wewnętrzne

                        // Wymuszenie przewijania poziomego
                        codeTextView.setHorizontallyScrolling(true); // Włącz przewijanie poziome
                        codeTextView.setScrollBarStyle(TextView.SCROLLBARS_INSIDE_OVERLAY);
                        codeTextView.setMovementMethod(new ScrollingMovementMethod()); // Aktywuj przesuwanie

                        // Zawsze wymuszanie szerokości w układzie
                        codeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, // TextView zajmuje szerokość ekranu
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        ));

                        // Dodaj TextView do HorizontalScrollView
                        horizontalScrollView.addView(codeTextView);

                        // Dodaj HorizontalScrollView do layoutu głównego
                        layout.addView(horizontalScrollView);

                        // Dodaj przycisk kopiowania kodu
                        Button copyButton = new Button(this);
                        copyButton.setText("Kopiuj kod");
                        copyButton.setOnClickListener(v -> copyToClipboard(section.getContent()));
                        layout.addView(copyButton);
                        break;

                    case LIST:
                        // Tworzenie sekcji listy
                        LinearLayout listLayout = new LinearLayout(this);
                        listLayout.setOrientation(LinearLayout.VERTICAL);

                        int id = 1;
                        for (String item : section.getListItems()) {
                            LinearLayout listItem = new LinearLayout(this);
                            TextView listItemDotView = new TextView(this);
                            TextView listItemView = new TextView(this);
                            listItem.setOrientation(LinearLayout.HORIZONTAL);
                            listItemDotView.setTypeface(null, Typeface.BOLD);
                            listItemView.setText(item); // Punkt listy
                            listItemDotView.setText(id + ". "); // Punkt listy
                            listItemView.setTextSize(16);
                            listItemView.setPadding(8, 8, 8, 8);
                            listItem.addView(listItemDotView);
                            listItem.addView(listItemView);
                            listLayout.addView(listItem);
                            id++;
                        }

                        layout.addView(listLayout);
                        break;

                    case EXAMPLE:
                        View exampleView = createExampleView(section.getContent());
                        int listViewHeight = 0;
                        if(section.getContent().equals("ListView")) {
                            ListView list = (ListView) exampleView;
                            listViewHeight = list.getAdapter().getCount() * 150;
                        }
                        layout.addView(exampleView);

                        ViewGroup.LayoutParams exampleViewLayoutParams = exampleView.getLayoutParams();

                        exampleViewLayoutParams.height = listViewHeight;
                        exampleView.setLayoutParams(exampleViewLayoutParams);
                        break;

                    case H1: // Obsługa nagłówków H1 do H6
                    case H2:
                    case H3:
                    case H4:
                    case H5:
                    case H6:
                        TextView headerView = new TextView(this);
                        headerView.setText(section.getContent());
                        headerView.setTypeface(null, Typeface.BOLD);

                        int textSize;
                        int marginTop;
                        switch (section.getType()) {
                            case H1: textSize = 26; marginTop = 80; break;
                            case H2: textSize = 24; marginTop = 64; break;
                            case H3: textSize = 22; marginTop = 48; break;
                            case H4: textSize = 20; marginTop = 32; break;
                            case H5: textSize = 18; marginTop = 24; break;
                            case H6: textSize = 16; marginTop = 16; break;
                            default: textSize = 16; marginTop = 8; break;
                        }
                        headerView.setTextSize(textSize);

                        // Tworzenie parametrów układu z marginesem
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(0, marginTop, 0, 0); // Marginesy: lewy, góra, prawy, dół
                        headerView.setLayoutParams(params); // Ustawiamy parametry dla TextView

                        layout.addView(headerView);
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

    private SpannableString highlightCode(String code) {
        SpannableString spannableString = new SpannableString(code);

        // Proste przykłady regexów do znalezienia kluczowych słów w Javie
        Pattern keywordPattern = Pattern.compile("\\b(public|double|private|protected|long|class|void|int|float|if|else|for|while|return|new)\\b");
        Pattern stringPattern = Pattern.compile("\".*?\""); // Ciągi znaków w cudzysłowach
        Pattern commentPattern = Pattern.compile("//.*|/\\*.*?\\*/", Pattern.DOTALL); // Komentarze
        Pattern fxmlPattern = Pattern.compile("@[A-Za-z]+", Pattern.DOTALL); // FXML
        Pattern dataTypePattern = Pattern.compile("\\b[A-Z][a-z]*([A-Z][a-z]*)?\\b"); // nazwy zmiennych
        Pattern regexPattern = Pattern.compile("^(.*?)-.*$"); //regexy (not working)

        // Ustaw kolory
        int keywordColor = Color.parseColor("#FF5722");
        int stringColor = Color.parseColor("#4CAF50");
        int commentColor = Color.parseColor("#757575");
        int fxmlColor = Color.parseColor("#f5c242");
        int dataTypeColor = Color.parseColor("#F000A7");
        int regexColor = Color.parseColor("#91366d");

        // Podświetl kluczowe słowa
        Matcher matcher = keywordPattern.matcher(code);
        while (matcher.find()) {
            spannableString.setSpan(
                    new ForegroundColorSpan(keywordColor),
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        // Podświetl typy danych
        matcher = dataTypePattern.matcher(code);
        while (matcher.find()) {
            spannableString.setSpan(
                    new ForegroundColorSpan(dataTypeColor),
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        // Podświetl ciągi znaków
        matcher = stringPattern.matcher(code);
        while (matcher.find()) {
            spannableString.setSpan(
                    new ForegroundColorSpan(stringColor),
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        // Podświetl komentarze
        matcher = commentPattern.matcher(code);
        while (matcher.find()) {
            spannableString.setSpan(
                    new ForegroundColorSpan(commentColor),
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        // Podświetl @FXML
        matcher = fxmlPattern.matcher(code);
        while (matcher.find()) {
            spannableString.setSpan(
                    new ForegroundColorSpan(fxmlColor),
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        // Podświetl regex
        matcher = regexPattern.matcher(code);
        while (matcher.find()) {
            spannableString.setSpan(
                    new ForegroundColorSpan(regexColor),
                    matcher.start(),
                    matcher.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        return spannableString;
    }


    private View createExampleView(String exampleType) {
        switch (exampleType) {
            case "ListView":
                ListView listView = new ListView(this);
                String[] listData = {"Element 1", "Element 2", "Element 3"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast toast = new Toast(getBaseContext());
                        toast.setText(((TextView) view).getText());
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

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
