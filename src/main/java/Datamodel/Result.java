package Datamodel;

import java.util.ArrayList;

public class Result {
    private int countTotalMistakes;
    private ArrayList<MistakeAnswer> mistakeAnswers = new ArrayList<MistakeAnswer>();

    public void addMistakeAnswer(MistakeAnswer answer) {
        this.countTotalMistakes++;
        this.mistakeAnswers.add(answer);
    }

    public int getCountTotalMistakes() {
        return countTotalMistakes;
    }

    public ArrayList<MistakeAnswer> getMistakeAnswers() {
        return mistakeAnswers;
    }
}
