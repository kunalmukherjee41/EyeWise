package com.technopie.eyewise.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.technopie.eyewise.R;
import com.technopie.eyewise.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private Button logout;
    private CardView questionCardView, aiCardView, hospitalCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionCardView = findViewById(R.id.questions_card_view);
        aiCardView = findViewById(R.id.ai_card_view);
        hospitalCardView = findViewById(R.id.hospital_card_view);

        logout = findViewById(R.id.logout);

        questionCardView.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, QuestionsActivity.class));
        });

        logout.setOnClickListener(view -> {
            SharedPrefManager.getInstance(this).clear();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }
}