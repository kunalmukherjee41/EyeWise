package com.technopie.eyewise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionsResponse {
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("questions")
    @Expose
    private List<Question> questions;

    public QuestionsResponse(Boolean error, List<Question> questions) {
        this.error = error;
        this.questions = questions;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
