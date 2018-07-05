package Datamodel;

import com.itextpdf.text.Paragraph;

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
        if(this.possibleAnswers.size() < 4) {
            this.possibleAnswers.add(possibleAnswer);
        }
    }

    public void addCorrectAnswer(int correctAnswer) {
        if(this.correctAnswers.size() < 4) {
            this.correctAnswers.add(correctAnswer);
        }
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
        this.possibleAnswers.clear();
        if(possibleAnswers.size() <=4) {
            this.possibleAnswers = possibleAnswers;
        } else {
            for(int i = 0; i < 4; i++) {
                this.possibleAnswers.add(possibleAnswers.get(i));
            }
        }
    }

    public ArrayList<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(ArrayList<Integer> correctAnswers) {
        this.correctAnswers.clear();
        if(correctAnswers.size() <= 4) {
            this.correctAnswers = correctAnswers;
        } else {
            for(int i = 0; i < 4; i++) {
                this.correctAnswers.add(correctAnswers.get(i));
            }
        }
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

    public int getdBId() {
        return dBId;
    }

    public void setdBId(int dBId) {
        this.dBId = dBId;
    }
}
