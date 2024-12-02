package com.example.inf04_edu_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        LinearLayout layout = findViewById(R.id.dynamicContentLayout);
        Item itemDetail = (Item) getIntent().getSerializableExtra("ITEM_DETAIL");

        if (itemDetail != null) {
            TextView titleTextView = new TextView(this);
            titleTextView.setText(itemDetail.getTitle());
            titleTextView.setTextSize(24);
            layout.addView(titleTextView);

            TextView descriptionTextView = new TextView(this);
            descriptionTextView.setText(itemDetail.getDescription());
            descriptionTextView.setTextSize(18);
            layout.addView(descriptionTextView);

            for (Item.Section section : itemDetail.getSections()) {
                switch (section.getType()) {
                    case TEXT:
                        createTextView(layout, section.getContent());
                        break;
                    case LIST:
                        createBulletList(layout, section.getContent());
                        break;
                    case CODE:
                        createCodeView(layout, section.getContent());
                        break;
                }
            }
        }
    }

    private void createTextView(LinearLayout layout, String content) {
        TextView textView = new TextView(this);
        textView.setText(content);
        textView.setTextSize(16);
        layout.addView(textView);
    }

    private void createBulletList(LinearLayout layout, String content) {
        for (String point : content.split("\n")) {
            TextView bulletPointView = new TextView(this);
            bulletPointView.setText("• " + point.trim());
            bulletPointView.setTextSize(16);
            layout.addView(bulletPointView);
        }
    }

    private void createCodeView(LinearLayout layout, String content) {
        SpannableString spannableCode = new SpannableString(content);
        applySyntaxHighlighting(spannableCode);

        TextView codeTextView = new TextView(this);
        codeTextView.setBackgroundResource(R.drawable.code_background);
        codeTextView.setText(spannableCode);
        layout.addView(codeTextView);
    }

    private void applySyntaxHighlighting(SpannableString code) {
        String[] keywords = {"public", "void", "int", "String", "class"};
        for (String keyword : keywords) {
            int start = code.toString().indexOf(keyword);
            if (start != -1) {
                code.setSpan(new ForegroundColorSpan(Color.BLUE), start, start + keyword.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}
