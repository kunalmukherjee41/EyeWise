package com.technopie.eyewise.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.technopie.eyewise.R;
import com.technopie.eyewise.api.RetrofitClient;
import com.technopie.eyewise.model.AnswerResponse1;
import com.technopie.eyewise.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
            isAnswered();
        });

        logout.setOnClickListener(view -> {
            SharedPrefManager.getInstance(this).clear();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    private void isAnswered() {
        Call<AnswerResponse1> call = RetrofitClient
                .getInstance()
                .getApi()
                .isAnswered(SharedPrefManager.getInstance(this).getUserId());

        call.enqueue(new Callback<AnswerResponse1>() {
            @Override
            public void onResponse(@NonNull Call<AnswerResponse1> call, @NonNull Response<AnswerResponse1> response) {
                assert response.body() != null;
                if (!response.body().getError()) {
                    if (response.body().isAnswered()) {
                        startActivity(new Intent(MainActivity.this, ReportActivity.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, QuestionsActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AnswerResponse1> call, @NonNull Throwable t) {

            }
        });
    }
}