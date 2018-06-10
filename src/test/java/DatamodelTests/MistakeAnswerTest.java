package DatamodelTests;

import Datamodel.MistakeAnswer;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MistakeAnswerTest {

    private int testQuestionId = 42;
    private String testQuestionPhrase = "this is a sample question";
    private String testPossibleAnswer1 = "sample1";
    private String testPossibleAnswer2 = "sample2";
    private String testPossibleAnswer3 = "sample3";
    private String testPossibleAnswer4 = "sample4";
    private ArrayList<String> testPossibleAnswersRight = new ArrayList<String>() {
        {
            add(testPossibleAnswer1);
            add(testPossibleAnswer2);
            add(testPossibleAnswer3);
            add(testPossibleAnswer4);
        }
    };
    private String testPossibleAnswer5 = "sample5";
    private ArrayList<String> testPossibleAnswersWrong = new ArrayList<String>() {
        {
            addAll(testPossibleAnswersRight);
            add(testPossibleAnswer5);
        }
    };
    private int testCorrectAnswer1 = 0;
    private int testCorrectAnswer2 = 1;
    private int testCorrectAnswer3 = 2;
    private int testCorrectAnswer4 = 3;
    private ArrayList<Integer> testCorrectAnswersRight = new ArrayList<Integer>() {
        {
            add(testCorrectAnswer1);
            add(testCorrectAnswer2);
            add(testCorrectAnswer3);
            add(testCorrectAnswer4);
        }
    };
    private int testCorrectAnswer5 = 4;
    private ArrayList<Integer> testCorrectAnswersWrong = new ArrayList<Integer>() {
        {
            addAll(testCorrectAnswersRight);
            add(testCorrectAnswer5);
        }
    };
    private String testGrammarSection = "sampleSection";
    private String testExercise = "page 42";
    private int testChosenAnswer1 = 0;
    private int testChosenAnswer2 = 1;
    private int testChosenAnswer3 = 2;
    private int testChosenAnswer4 = 3;
    private int testChosenAnswer5 = 4;
    private ArrayList<Integer> testChosenAnswersRight = new ArrayList<Integer>() {
        {
            add(testChosenAnswer1);
            add(testChosenAnswer2);
            add(testChosenAnswer3);
            add(testChosenAnswer4);
        }
    };
    private ArrayList<Integer> testChosenAnswersWrong = new ArrayList<Integer>() {
        {
            addAll(testChosenAnswersRight);
            add(testChosenAnswer5);
        }
    };

    @Test
    public void testCreateMistakeAnswerObject() {
        MistakeAnswer mistakeAnswer = new MistakeAnswer(testQuestionId, testQuestionPhrase, testChosenAnswersRight, testCorrectAnswersRight, testPossibleAnswersRight, testGrammarSection, testExercise);
        assertEquals(testQuestionId, mistakeAnswer.getQuestionId());
        assertEquals(testQuestionPhrase, mistakeAnswer.getQuestionPhrase());
        int i = 0;
        for(Integer chosenAnswer:testChosenAnswersRight) {
            assertEquals(chosenAnswer, mistakeAnswer.getChosenAnswers().get(i));
            i++;
        }
        i = 0;
        for(Integer correctAnswer: testCorrectAnswersRight) {
            assertEquals(correctAnswer, mistakeAnswer.getCorrectAnswers().get(i));
            i++;
        }
        i = 0;
        for(String possibleAnswer: testPossibleAnswersRight) {
            assertEquals(possibleAnswer, mistakeAnswer.getPossibleAnswers().get(i));
            i++;
        }
        assertEquals(testGrammarSection, mistakeAnswer.getGrammarSection());
        assertEquals(testExercise, mistakeAnswer.getExercise());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testCreateMistakeAnswerObjectWithWrongChosenAnswers() {
        MistakeAnswer mistakeAnswer = new MistakeAnswer(testQuestionId, testQuestionPhrase, testChosenAnswersWrong, testCorrectAnswersRight, testPossibleAnswersRight, testGrammarSection, testExercise);
        assertEquals(testChosenAnswer1, (int) mistakeAnswer.getChosenAnswers().get(0));
        assertEquals(testChosenAnswer2, (int) mistakeAnswer.getChosenAnswers().get(1));
        assertEquals(testChosenAnswer3, (int) mistakeAnswer.getChosenAnswers().get(2));
        assertEquals(testChosenAnswer4, (int) mistakeAnswer.getChosenAnswers().get(3));
        assertNull(mistakeAnswer.getChosenAnswers().get(4));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testCreateMistakeAnswerObjectWithWrongCorrectAnswers() {
        MistakeAnswer mistakeAnswer = new MistakeAnswer(testQuestionId, testQuestionPhrase, testChosenAnswersRight, testCorrectAnswersWrong, testPossibleAnswersRight, testGrammarSection, testExercise);
        assertEquals(testCorrectAnswer1, (int) mistakeAnswer.getCorrectAnswers().get(0));
        assertEquals(testCorrectAnswer2, (int) mistakeAnswer.getCorrectAnswers().get(1));
        assertEquals(testCorrectAnswer3, (int) mistakeAnswer.getCorrectAnswers().get(2));
        assertEquals(testCorrectAnswer4, (int) mistakeAnswer.getCorrectAnswers().get(3));
        assertNull(mistakeAnswer.getChosenAnswers().get(4));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testCreateMistakeAnswerObjectWithWrongPossibleAnswers() {
        MistakeAnswer mistakeAnswer = new MistakeAnswer(testQuestionId, testQuestionPhrase, testChosenAnswersRight, testCorrectAnswersRight, testPossibleAnswersWrong, testGrammarSection, testExercise);
        assertEquals(testPossibleAnswer1, mistakeAnswer.getPossibleAnswers().get(0));
        assertEquals(testPossibleAnswer2, mistakeAnswer.getPossibleAnswers().get(1));
        assertEquals(testPossibleAnswer3, mistakeAnswer.getPossibleAnswers().get(2));
        assertEquals(testPossibleAnswer4, mistakeAnswer.getPossibleAnswers().get(3));
        assertNull(mistakeAnswer.getPossibleAnswers().get(4));
    }
}
