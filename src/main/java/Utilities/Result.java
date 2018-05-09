package Utilities;

import java.util.ArrayList;

public class Result {
    private int countTotalMistakes;
    private ArrayList<MistakeAnswer> mistakeAnswers = new ArrayList<MistakeAnswer>();

    public void addMistakeToCountTotalMistakes() {
        this.countTotalMistakes++;
    }

    public void addAnswerToMistakeAnswers(MistakeAnswer answer) {
        this.mistakeAnswers.add(answer);
    }

    public int getCountTotalMistakes() {
        return countTotalMistakes;
    }

    public void setCountTotalMistakes(int countTotalMistakes) {
        this.countTotalMistakes = countTotalMistakes;
    }

    public ArrayList<MistakeAnswer> getMistakeAnswers() {
        return mistakeAnswers;
    }

    public void setMistakeAnswers(ArrayList<MistakeAnswer> mistakeAnswers) {
        this.mistakeAnswers = mistakeAnswers;
    }
}
