package com.example.quizapp.Domain;

import java.util.ArrayList;

public class Question {
    private String text;
    private ArrayList<Answer> answers;

    public Question(String text, ArrayList<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }
}
