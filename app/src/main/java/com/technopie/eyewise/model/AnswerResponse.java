package com.technopie.eyewise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnswerResponse {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("answers")
    @Expose
    private List<Answer> answers;

    public AnswerResponse(Boolean error, List<Answer> answers) {
        this.error = error;
        this.answers = answers;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
