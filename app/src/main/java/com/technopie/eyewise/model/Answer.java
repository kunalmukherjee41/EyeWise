package com.technopie.eyewise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {
    @SerializedName("userfeed_id")
    @Expose
    private int userFeedId;

    @SerializedName("refuser_id")
    @Expose
    private String refUserId;

    @SerializedName("refquestion_id")
    @Expose
    private String refQuestionId;

    @SerializedName("answer")
    @Expose
    private String answer;

    public Answer(int userFeedId, String refUserId, String refQuestionId, String answer) {
        this.userFeedId = userFeedId;
        this.refUserId = refUserId;
        this.refQuestionId = refQuestionId;
        this.answer = answer;
    }

    public int getUserFeedId() {
        return userFeedId;
    }

    public void setUserFeedId(int userFeedId) {
        this.userFeedId = userFeedId;
    }

    public String getRefUserId() {
        return refUserId;
    }

    public void setRefUserId(String refUserId) {
        this.refUserId = refUserId;
    }

    public String getRefQuestionId() {
        return refQuestionId;
    }

    public void setRefQuestionId(String refQuestionId) {
        this.refQuestionId = refQuestionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
