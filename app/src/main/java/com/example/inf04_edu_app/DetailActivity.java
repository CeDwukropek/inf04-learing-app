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

        if (itemDetail != null) {
            // Wyświetlanie tytułu
            TextView titleTextView = new TextView(this);
            titleTextView.setText(itemDetail.getTitle());
            titleTextView.setTextSize(24);
            layout.addView(titleTextView);

            // Wyświetlanie opisu
            TextView descriptionTextView = new TextView(this);
            descriptionTextView.setText(itemDetail.getDescription());
            descriptionTextView.setTextSize(18);
            layout.addView(descriptionTextView);

            // Wyświetlanie sekcji tekstowych
            for (String text : itemDetail.getTextSections()) {
                TextView textView = new TextView(this);
                textView.setText(text);
                textView.setTextSize(16);
                layout.addView(textView);
            }

            // Wyświetlanie obrazów
            for (int imageResId : itemDetail.getImageResIds()) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(imageResId);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 300
                ));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                layout.addView(imageView);
            }

            // Wyświetlanie listy punktów
            for (String point : itemDetail.getBulletPoints()) {
                TextView bulletPointView = new TextView(this);
                bulletPointView.setText("• " + point);
                bulletPointView.setTextSize(16);
                layout.addView(bulletPointView);
            }
        }

        // Przykładowy fragment kodu
        String codeSnippet = "public void exampleMethod() {\n   System.out.println(\"Hello, world!\");\n}";

        // Tworzenie sekcji kodu
        TextView codeTextView = new TextView(this);
        SpannableString spannableCode = new SpannableString(codeSnippet);
        applySyntaxHighlighting(spannableCode);
        codeTextView.setBackgroundResource(R.drawable.code_background);

        codeTextView.setText(spannableCode);
        layout.addView(codeTextView);

        // Przycisk do kopiowania kodu
        Button copyButton = new Button(this);
        copyButton.setText("Kopiuj kod");
        copyButton.setOnClickListener(v -> copyToClipboard(codeSnippet));
        layout.addView(copyButton);

        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void applySyntaxHighlighting(SpannableString code) {
        // Kolorowanie słów kluczowych
        String[] keywords = {"public", "void", "System", "out", "println"};
        for (String keyword : keywords) {
            Pattern pattern = Pattern.compile("\\b" + keyword + "\\b");
            Matcher matcher = pattern.matcher(code);
            while (matcher.find()) {
                code.setSpan(new ForegroundColorSpan(Color.BLUE), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        // Kolorowanie komentarzy
        Pattern commentPattern = Pattern.compile("//.*");
        Matcher commentMatcher = commentPattern.matcher(code);
        while (commentMatcher.find()) {
            code.setSpan(new ForegroundColorSpan(Color.GREEN), commentMatcher.start(), commentMatcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        // Kolorowanie ciągów znaków (stringów)
        Pattern stringPattern = Pattern.compile("\".*?\"");
        Matcher stringMatcher = stringPattern.matcher(code);
        while (stringMatcher.find()) {
            code.setSpan(new ForegroundColorSpan(Color.MAGENTA), stringMatcher.start(), stringMatcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Code Snippet", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Kod skopiowany do schowka!", Toast.LENGTH_SHORT).show();
    }
}
