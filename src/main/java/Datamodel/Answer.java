package Datamodel;

import java.util.ArrayList;

public class Answer {
    private int id;
    private int questionId;
    private String questionPhrase;
    private String grammarSection;
    private String exercise;
    private ArrayList<String> possibleAnswers = new ArrayList<>();
    private ArrayList<Integer> correctAnswers = new ArrayList<>();
    private ArrayList<Integer> chosenAnswers = new ArrayList<Integer>();

    public Answer(int id, int questionId, String questionPhrase, String grammarSection, String exercise) {
        this.setId(id);
        this.setQuestionId(questionId);
        this.setQuestionPhrase(questionPhrase);
        this.setGrammarSection(grammarSection);
        this.setExercise(exercise);
    }

    public Answer(int questionId, String questionPhrase, String grammarSection, String exercise, ArrayList<String> possibleAnswers, ArrayList<Integer> correctAnswers) {
        this.setQuestionId(questionId);
        this.setQuestionPhrase(questionPhrase);
        this.setGrammarSection(grammarSection);
        this.setExercise(exercise);
        this.setPossibleAnswers(possibleAnswers);
        this.setCorrectAnswers(correctAnswers);
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

    public void addChosenAnswer(int chosenAnswer) {
        this.chosenAnswers.add(chosenAnswer);
    }

    public void addPossibleAnswer(String possibleAnswer) { this.possibleAnswers.add(possibleAnswer);}

    public void addCorrectAnswer(int correctAnswer) { this.correctAnswers.add(correctAnswer);}

    public ArrayList<Integer> getChosenAnswers() {
        return chosenAnswers;
    }

    public void setChosenAnswers(ArrayList<Integer> chosenAnswers) {
        this.chosenAnswers = chosenAnswers;
    }

    public String getQuestionPhrase() {
        return questionPhrase;
    }

    public void setQuestionPhrase(String questionPhrase) {
        this.questionPhrase = questionPhrase;
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
}
