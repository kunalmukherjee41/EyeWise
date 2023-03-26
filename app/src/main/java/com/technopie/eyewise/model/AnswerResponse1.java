package com.technopie.eyewise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerResponse1 {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("isanswered")
    @Expose
    private boolean isAnswered;

    public AnswerResponse1(Boolean error, boolean isAnswered) {
        this.error = error;
        this.isAnswered = isAnswered;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
