package com.djenterprise.app.game;

public class AnswerBO {

    private int answerId;
    private String text;
    private int questionId;
    private boolean truthfulness; //TODO Be careful! We need proper assigning BIT() -> boolean

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isCorrect() {
        return truthfulness;
    }

    public void setTruthfulness(int truthfulness) {
        this.truthfulness = truthfulness != 0;
    }
}