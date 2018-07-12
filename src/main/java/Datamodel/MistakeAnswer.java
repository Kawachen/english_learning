package Datamodel;

import java.util.ArrayList;

public class MistakeAnswer {

    private int questionId;
    private String questionPhrase;
    private String grammarSection;
    private String exercise;
    private ArrayList<Integer> chosenAnswers = new ArrayList<>();
    private ArrayList<Integer> correctAnswers = new ArrayList<>();
    private ArrayList<String> possibleAnswers = new ArrayList<>();

    public MistakeAnswer(int questionId, String questionPhrase, ArrayList<Integer> chosenAnswers, ArrayList<Integer> correctAnswers, ArrayList<String> possibleAnswers, String grammarSection, String exercise) {
        this.setQuestionId(questionId);
        this.setQuestionPhrase(questionPhrase);
        this.setChosenAnswers(chosenAnswers);
        this.setCorrectAnswers(correctAnswers);
        this.setPossibleAnswers(possibleAnswers);
        this.setGrammarSection(grammarSection);
        this.setExercise(exercise);
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
        this.chosenAnswers.clear();
        if(chosenAnswers.size() <=4) {
            this.chosenAnswers = chosenAnswers;
        } else {
            for(int i = 0; i < 4; i++) {
                this.chosenAnswers.add(chosenAnswers.get(i));
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
