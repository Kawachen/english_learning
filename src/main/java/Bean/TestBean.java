package Bean;

import Utilities.Question;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;

@ManagedBean
@RequestScoped
public class TestBean {


    private Question question;
    private String[] possibleAnswers = new String[4];
    private int[] correctAnswers = new int[4];

    public TestBean() {
        ArrayList<String> possibleAnswerList = new ArrayList<>();
        possibleAnswerList.add("blub");
        possibleAnswerList.add("bli");
        possibleAnswerList.add("bumms");
        ArrayList<Integer> correctAnswerList = new ArrayList<>();
        correctAnswerList.add(1);
        question = new Question("bla", possibleAnswerList, correctAnswerList, "sample", "bam");
        for (int s = 0; s < possibleAnswers.length; s++) {
            possibleAnswers[s] = "";
        }
        for (int i = 0; i < correctAnswers.length; i++) {
            correctAnswers[i] = -1;
        }
    }

    public void test() {
        ArrayList<String> possibleAnswerList = new ArrayList<>();
        for (int i = 0; i < possibleAnswers.length; i++) {
            if(!possibleAnswers[i].equalsIgnoreCase("")) {
                possibleAnswerList.add(possibleAnswers[i]);
            }
        }
        question.setPossibleAnswers(possibleAnswerList);
        ArrayList<Integer> correctAnswerList = new ArrayList<>();

        for (int s = 0; s < correctAnswers.length; s++) {
            if(correctAnswers[s] != -1) {
                correctAnswerList.add(correctAnswers[s]);
            }
        }
        question.setCorrectAnswers(correctAnswerList);
        question.getCorrectAnswers().forEach(correctAnswers -> System.out.println(correctAnswers));
        question.getPossibleAnswers().forEach(possibleAnswers -> System.out.println(possibleAnswers));
        clearCorrectAnswers();
        clearPossibleAnswers();
    }

    public String[] getPossibleAnswers() {
        for (int i = 0; i < possibleAnswers.length; i++) {
            if(i < question.getPossibleAnswers().size()) possibleAnswers[i] = question.getPossibleAnswers().get(i);
        }
        return possibleAnswers;
    }

    public void setPossibleAnswers(String[] possibleAnswers) {
        for (int i = 0; i < possibleAnswers.length; i++) {
            System.out.println(possibleAnswers[i]);
        }
        this.possibleAnswers = possibleAnswers;
    }

    public int[] getCorrectAnswers() {
        for (int i = 0; i < correctAnswers.length; i++) {
            if(i < question.getCorrectAnswers().size()) correctAnswers[i] = question.getCorrectAnswers().get(i);
        }
        return correctAnswers;
    }

    public void setCorrectAnswers(int[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    private void clearCorrectAnswers() {
        for (int i = 0; i < correctAnswers.length; i++) {
            correctAnswers[i] = -1;
        }
    }

    private void clearPossibleAnswers() {
        for (int i = 0; i < possibleAnswers.length; i++) {
            possibleAnswers[i] = "";
        }
    }
}
