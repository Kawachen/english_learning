package Utilities;

import java.util.ArrayList;

public class Answer {
    private int id;
    private int questionId;
    private String questionPhrase;
    private ArrayList<Integer> chosenAnswers = new ArrayList<Integer>();

    public Answer(int questionId, String questionPhrase) {
        this.setQuestionId(questionId);
        this.setQuestionPhrase(questionPhrase);
    }

    public Answer() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionPhrase() {
        return questionPhrase;
    }

    public void setQuestionPhrase(String questionPhrase) {
        this.questionPhrase = questionPhrase;
    }

    public void addChosenAnswer(int chosenAnswer) {
        this.chosenAnswers.add(chosenAnswer);
    }

    public ArrayList<Integer> getChosenAnswers() {
        return chosenAnswers;
    }

    public void setChosenAnswers(ArrayList<Integer> chosenAnswers) {
        this.chosenAnswers = chosenAnswers;
    }
}
