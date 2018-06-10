package DatamodelTests;

import Datamodel.MistakeAnswer;
import Datamodel.Result;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ResultTest {

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
    private String testGrammarSection = "sampleSection";
    private String testExercise = "page 42";
    private int testChosenAnswer1 = 0;
    private int testChosenAnswer2 = 1;
    private int testChosenAnswer3 = 2;
    private int testChosenAnswer4 = 3;
    private ArrayList<Integer> testChosenAnswersRight = new ArrayList<Integer>() {
        {
            add(testChosenAnswer1);
            add(testChosenAnswer2);
            add(testChosenAnswer3);
            add(testChosenAnswer4);
        }
    };
    private MistakeAnswer mistakeAnswer = new MistakeAnswer(testQuestionId, testQuestionPhrase, testChosenAnswersRight, testCorrectAnswersRight, testPossibleAnswersRight, testGrammarSection, testExercise);

    @Test
    public void testCreateResultObject() {
        Result result = new Result();
        assertEquals(Result.class, result.getClass());
    }

    @Test
    public void testAddMistakeAnswer() {
        Result result = new Result();
        result.addMistakeAnswer(mistakeAnswer);
        assertEquals(1, result.getCountTotalMistakes());
        assertEquals(42, result.getMistakeAnswers().get(0).getQuestionId());
    }
}
