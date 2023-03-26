package com.technopie.eyewise.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("questionnaire_id")
    @Expose
    private int questionnaireId;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("question_type")
    @Expose
    private String questionType;

    @SerializedName("answer_key")
    @Expose
    private String answerKey;

    @SerializedName("priority")
    @Expose
    private String priority;

    @SerializedName("imagename")
    @Expose
    private String imageName;

    public Question(int questionnaireId, String category, String question, String questionType, String answerKey, String priority, String imageName) {
        this.questionnaireId = questionnaireId;
        this.category = category;
        this.question = question;
        this.questionType = questionType;
        this.answerKey = answerKey;
        this.priority = priority;
        this.imageName = imageName;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswerKey() {
        return answerKey;
    }

    public void setAnswerKey(String answerKey) {
        this.answerKey = answerKey;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionnaireId=" + questionnaireId +
                ", category='" + category + '\'' +
                ", question='" + question + '\'' +
                ", questionType='" + questionType + '\'' +
                ", answerKey='" + answerKey + '\'' +
                ", priority='" + priority + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
