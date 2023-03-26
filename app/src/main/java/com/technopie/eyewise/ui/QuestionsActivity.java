package com.technopie.eyewise.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.technopie.eyewise.R;
import com.technopie.eyewise.api.RetrofitClient;
import com.technopie.eyewise.model.Question;
import com.technopie.eyewise.model.QuestionsResponse;
import com.technopie.eyewise.storage.SharedPrefManager;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends AppCompatActivity {

    private List<Question> questions;
    RadioGroup radioGroup;
    private String answer;
    private TextView questionId;
    private ImageView questionsImage;
    private Button submit;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        getQuestions();

        radioGroup = findViewById(R.id.radio_group);
        questionId = findViewById(R.id.question_id);
        questionsImage = findViewById(R.id.questions_image);
        submit = findViewById(R.id.submit);

        radioGroup.setOnCheckedChangeListener((group, id) -> {
            answer = (R.id.radio_yes == id) ? "Yes" : "No";
        });

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
                if (!response.body().getError()) {
                    questions = response.body().getQuestions();
                    setQuestion();
                }
            }

            @Override
            public void onFailure(@NonNull Call<QuestionsResponse> call, @NonNull Throwable t) {
                Toast.makeText(QuestionsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setQuestion() {
        Question question = questions.get(i++);
        questionId.setText("Q. ".concat(question.getQuestion()));
        Glide.with(this).load("https://technopie.in/rest_api/images/" + question.getImageName()).into(questionsImage);
        submit.setOnClickListener(v -> {
            saveAnswer(question.getQuestionnaireId());
            radioGroup.clearCheck();
            if (questions.size() > i) {
                setQuestion();
            } else {
                Toast.makeText(this, "All Question Are Answered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }
        });
    }

    private void saveAnswer(int refQuestionId) {
        int refUserId = SharedPrefManager.getInstance(this).getUserId();
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .saveAnswers(refUserId, refQuestionId, answer);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                assert response.body() != null;
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(QuestionsActivity.this, "Failed To Save Your Answer", Toast.LENGTH_SHORT).show();
            }
        });
    }
}