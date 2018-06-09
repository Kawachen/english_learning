package DatamodelTests;

import Datamodel.Question;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QuestionTest {

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
    private ArrayList<Integer> testCorrectAnswerWrong = new ArrayList<Integer>() {
        {
            addAll(testCorrectAnswersRight);
            add(testCorrectAnswer5);
        }
    };
    private String testGrammarSection = "sampleSection";
    private String testExercise = "page 42";

    @Test
    public void testCreateQuestionObject() {
        Question question = new Question();
        assertEquals(Question.class, question.getClass());
        Question question1 = new Question(testQuestionPhrase, testPossibleAnswersRight, testCorrectAnswersRight, testGrammarSection, testExercise);
        assertEquals(testQuestionPhrase, question.getQuestionPhrase());
        int i = 0;
        for (String testPossibleAnswer: testPossibleAnswersRight) {
            assertEquals(testPossibleAnswer, question.getPossibleAnswers().get(i));
            i++;
        }
        int s = 0;
        for (Integer testCorrectAnswer: testCorrectAnswersRight) {
            assertEquals(testCorrectAnswer, question.getCorrectAnswers().get(s));
            s++;
        }
        assertEquals(testGrammarSection, question.getGrammarSection());
        assertEquals(testExercise, question.getExercise());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testCreateQuestionObjectWithWrongPossibleAnswers() {
        Question question = new Question(testQuestionPhrase, testPossibleAnswersWrong, testCorrectAnswersRight, testGrammarSection, testExercise);
        assertNull(question.getPossibleAnswers().get(4));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testCreateQuestionObjectWithWrongCorrectAnswers() {
        Question question = new Question(testQuestionPhrase, testPossibleAnswersRight, testCorrectAnswerWrong, testGrammarSection, testExercise);
        assertNull(question.getCorrectAnswers().get(4));
    }
}
