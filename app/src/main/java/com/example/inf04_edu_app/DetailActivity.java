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
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        Item itemDetail = (Item) getIntent().getSerializableExtra("ITEM_DETAIL");

        for (ItemContent content : itemDetail.getContents()) {
            switch (content.getContentType()) {
                case ItemContent.TYPE_TEXT:
                    TextView textView = new TextView(this);
                    textView.setText(content.getText());
                    textView.setTextSize(18);
                    layout.addView(textView);
                    break;

                case ItemContent.TYPE_LIST:
                    for (String item : content.getListItems()) {
                        TextView listItemView = new TextView(this);
                        listItemView.setText("• " + item);
                        listItemView.setTextSize(16);
                        layout.addView(listItemView);
                    }
                    break;

                case ItemContent.TYPE_CODE:
                    // Tworzenie TextView dla kodu
                    TextView codeTextView = new TextView(this);
                    SpannableString spannableCode = new SpannableString(content.getCodeSnippet());
                    applySyntaxHighlighting(spannableCode);

                    // Ustawienia TextView dla przewijania poziomego i niezawijania tekstu
                    codeTextView.setText(spannableCode);
                    codeTextView.setTextSize(16);
                    codeTextView.setPadding(16, 16, 16, 16);
                    codeTextView.setHorizontallyScrolling(true);  // Wymuszenie przewijania poziomego
                    codeTextView.setSingleLine(false);            // Pozwala na wiele linii bez zawijania
                    codeTextView.setBackgroundResource(R.drawable.code_background); // Ustawienie tła

                    // Tworzenie HorizontalScrollView i dodanie TextView do niego
                    HorizontalScrollView scrollView = new HorizontalScrollView(this);
                    scrollView.addView(codeTextView);

                    // Dodanie HorizontalScrollView do układu
                    layout.addView(scrollView);


                    Button copyButton = new Button(this);
                    copyButton.setText("Kopiuj kod");
                    copyButton.setOnClickListener(v -> copyToClipboard(content.getCodeSnippet()));
                    layout.addView(copyButton);
                    break;
            }
        }


        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void applySyntaxHighlighting(SpannableString code) {
        // Kolorowanie słów kluczowych Javy
        String[] keywords = {
                "public", "private", "protected", "class", "static", "void", "int",
                "double", "float", "boolean", "if", "else", "for", "while", "switch",
                "case", "break", "continue", "return", "try", "catch", "finally", "throw",
                "throws", "new", "this", "super", "extends", "implements", "import",
                "package", "default", "null", "true", "false"
        };

        String[] dataTypes = {};

        highlightWords(code, keywords, Color.BLUE);

        // Kolorowanie komentarzy jednoliniowych
        highlightPattern(code, "//.*", Color.GREEN);

        // Kolorowanie komentarzy wieloliniowych
        highlightPattern(code, "/\\*.*?\\*/", Color.GREEN);

        // Kolorowanie stringów (ciągów znaków)
        highlightPattern(code, "\"(\\\\.|[^\"])*\"", Color.MAGENTA);

        // Kolorowanie literałów liczbowych
        highlightPattern(code, "\\b\\d+\\b", Color.RED);
    }

    private void highlightWords(SpannableString code, String[] words, int color) {
        for (String word : words) {
            Pattern pattern = Pattern.compile("\\b" + word + "\\b");
            Matcher matcher = pattern.matcher(code);
            while (matcher.find()) {
                code.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    private void highlightPattern(SpannableString code, String regex, int color) {
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL); // DOTALL obsługuje wieloliniowe dopasowania
        Matcher matcher = pattern.matcher(code);
        while (matcher.find()) {
            code.setSpan(new ForegroundColorSpan(color), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }


    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Code Snippet", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Kod skopiowany do schowka!", Toast.LENGTH_SHORT).show();
    }
}
