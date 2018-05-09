package Utilities;

import java.util.ArrayList;

public class MistakeAnswer {

    private int questionId;
    private String questionPhrase;
    private String grammarSection;
    private String exercise;
    private ArrayList<Integer> chosenAnswers = new ArrayList<Integer>();
    private ArrayList<Integer> correctAnswers = new ArrayList<Integer>();
    private ArrayList<String> possibleAnswers = new ArrayList<String>();

    public MistakeAnswer(int questionId, String questionPhrase, ArrayList<Integer> chosenAnswers, ArrayList<Integer> correctAnswers, ArrayList<String> possibleAnswers, String grammarSection, String exercise) {
        this.questionId = questionId;
        this.questionPhrase = questionPhrase;
        this.grammarSection = grammarSection;
        this.chosenAnswers = chosenAnswers;
        this.correctAnswers = correctAnswers;
        this.possibleAnswers = possibleAnswers;
        this.exercise = exercise;
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

    public ArrayList<Integer> getChosenAnswers() {
        return chosenAnswers;
    }

    public void setChosenAnswers(ArrayList<Integer> chosenAnswers) {
        this.chosenAnswers = chosenAnswers;
    }

    public ArrayList<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(ArrayList<Integer> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public ArrayList<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(ArrayList<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public String getGrammarSection() {
        return grammarSection;
    }

    public void setGrammarSection(String grammarSection) {
        this.grammarSection = grammarSection;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }
}
