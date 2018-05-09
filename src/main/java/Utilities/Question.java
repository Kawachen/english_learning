package Utilities;

import java.util.ArrayList;

public class Question {

    private int id;
    private int dBId;
    private String questionPhrase;
    private ArrayList possibleAnswers = new ArrayList<String>();
    private ArrayList correctAnswers = new ArrayList<Integer>();
    private String grammarSection;
    private String exercise;

    public Question(String questionPhrase, ArrayList<String> possibleAnswers, ArrayList<Integer> correctAnswers, String grammarSection, String exercise) {
        this.setQuestionPhrase(questionPhrase);
        this.setPossibleAnswers(possibleAnswers);
        this.setCorrectAnswers(correctAnswers);
        this.setGrammarSection(grammarSection);
        this.setExercise(exercise);
    }

    public Question(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addPossibleAnswer(String possibleAnswer) {
        this.possibleAnswers.add(possibleAnswer);
    }

    public void addCorrectAnswer(int correctAnswer) {
        this.correctAnswers.add(correctAnswer);
    }

    public String getQuestionPhrase() {
        return questionPhrase;
    }

    public void setQuestionPhrase(String questionPhrase) {
        this.questionPhrase = questionPhrase;
    }

    public ArrayList<String> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(ArrayList<String> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public ArrayList<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(ArrayList<Integer> correctAnswers) {
        this.correctAnswers = correctAnswers;
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
