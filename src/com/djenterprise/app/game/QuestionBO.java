package com.djenterprise.app.game;

import java.util.ArrayList;
import java.util.List;

public class QuestionBO {

    private int questionId;
    private String text;
    private List<AnswerBO> answers = new ArrayList<>();

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

    public void addAnswer (AnswerBO answer){
        answers.add(answer);
    }

    public List<AnswerBO> getAnswers(){
        return answers;
    }
}