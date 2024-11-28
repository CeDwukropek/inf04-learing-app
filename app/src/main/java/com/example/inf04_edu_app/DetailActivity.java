package com.example.inf04_edu_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        LinearLayout layout = findViewById(R.id.dynamicContentLayout);

        Button backButton = findViewById(R.id.backButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ArrayList<String> texts = extras.getStringArrayList("TEXTS");

            if (texts != null) {
                for (String text : texts) {
                    TextView textView = new TextView(this);
                    textView.setText(text);
                    textView.setTextSize(18);
                    layout.addView(textView);
                }
            }
        }

        // Obsługa kliknięcia przycisku powrotu
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Zakończenie DetailActivity i powrót do MainActivity
            }
        });
    }
}
