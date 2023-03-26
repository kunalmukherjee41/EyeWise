package com.technopie.eyewise.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.technopie.eyewise.R;
import com.technopie.eyewise.adapter.QuestionAnswerListAdapter;
import com.technopie.eyewise.api.RetrofitClient;
import com.technopie.eyewise.model.Answer;
import com.technopie.eyewise.model.AnswerResponse;
import com.technopie.eyewise.model.Question;
import com.technopie.eyewise.model.QuestionAnswer;
import com.technopie.eyewise.model.QuestionsResponse;
import com.technopie.eyewise.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<QuestionAnswer> questionAnswers;
    private List<Answer> answers;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        getQuestions();
    }

    private void getQuestions() {
        Call<QuestionsResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllQuestions();

        call.enqueue(new Callback<QuestionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuestionsResponse> call, @NonNull Response<QuestionsResponse> response) {
                assert response.body() != null;
                if (!response.body().getError())
                    questions = response.body().getQuestions();
                getAnswers();
            }

            @Override
            public void onFailure(@NonNull Call<QuestionsResponse> call, @NonNull Throwable t) {
                Toast.makeText(ReportActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getAnswers() {
        Call<AnswerResponse> call1 = RetrofitClient
                .getInstance()
                .getApi()
                .getAnswersByUserId(SharedPrefManager.getInstance(ReportActivity.this).getUserId());

        call1.enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(@NonNull Call<AnswerResponse> call, @NonNull Response<AnswerResponse> response) {
                assert response.body() != null;
                if (!response.body().getError()) {
                    answers = response.body().getAnswers();
                    bindQuestionAndAnswers();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AnswerResponse> call, @NonNull Throwable t) {
                Toast.makeText(ReportActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindQuestionAndAnswers() {
        questionAnswers = new ArrayList<>();
        for (int i = 0; i < answers.size(); i++) {
            questionAnswers.add(new QuestionAnswer(questions.get(i).getQuestion(), answers.get(i).getAnswer()));
        }
        QuestionAnswerListAdapter adapter = new QuestionAnswerListAdapter(ReportActivity.this, questionAnswers);
        recyclerView.setAdapter(adapter);
    }
}