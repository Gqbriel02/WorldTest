package com.example.worldtest;

public class Question {
    private String question;
    private String[] ansOptions;
    private int corrOption;

    public Question(String question, String[] ansOptions, int corrOption) {
        this.question = question;
        this.ansOptions = ansOptions;
        this.corrOption = corrOption;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return ansOptions;
    }

    public int getCorrectOption() {
        return corrOption;
    }
}
