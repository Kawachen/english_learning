package DatamodelTests;

import Datamodel.Question;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QuestionTest {

    private int testId = 42;
    private int testDbId = 13;
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
        }
    };
    private ArrayList<String> testPossibleAnswersRight2 = new ArrayList<String>() {
        {
            addAll(testPossibleAnswersRight);
            add(testPossibleAnswer4);
        }
    };
    private String testPossibleAnswer5 = "sample5";
    private ArrayList<String> testPossibleAnswersWrong = new ArrayList<String>() {
        {
            addAll(testPossibleAnswersRight2);
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
        }
    };
    private ArrayList<Integer> testCorrectAnswersRight2 = new ArrayList<Integer>() {
        {
            addAll(testCorrectAnswersRight);
            add(testCorrectAnswer4);
        }
    };
    private int testCorrectAnswer5 = 4;
    private ArrayList<Integer> testCorrectAnswerWrong = new ArrayList<Integer>() {
        {
            addAll(testCorrectAnswersRight2);
            add(testCorrectAnswer5);
        }
    };
    private String testGrammarSection = "sampleSection";
    private String testExercise = "page 42";

    @Test
    public void testCreateQuestionObjectWith4PossibleAndCorrectAnswers() {
        Question question = new Question();
        assertEquals(Question.class, question.getClass());
        Question question1 = new Question(testQuestionPhrase, testPossibleAnswersRight2, testCorrectAnswersRight2, testGrammarSection, testExercise);
        assertEquals(testQuestionPhrase, question1.getQuestionPhrase());
        int i = 0;
        for (String testPossibleAnswer: testPossibleAnswersRight2) {
            assertEquals(testPossibleAnswer, question1.getPossibleAnswers().get(i));
            i++;
        }
        int s = 0;
        for (Integer testCorrectAnswer: testCorrectAnswersRight2) {
            assertEquals(testCorrectAnswer, question1.getCorrectAnswers().get(s));
            s++;
        }
        assertEquals(testGrammarSection, question1.getGrammarSection());
        assertEquals(testExercise, question1.getExercise());
    }

    @Test
    public void testQuestionObjectWith3PossibleAndCorrectAnswers() {
        Question question1 = new Question(testQuestionPhrase, testPossibleAnswersRight, testCorrectAnswersRight, testGrammarSection, testExercise);
        assertEquals(testQuestionPhrase, question1.getQuestionPhrase());
        int i = 0;
        for (String testPossibleAnswer: testPossibleAnswersRight) {
            assertEquals(testPossibleAnswer, question1.getPossibleAnswers().get(i));
            i++;
        }
        int s = 0;
        for (Integer testCorrectAnswer: testCorrectAnswersRight) {
            assertEquals(testCorrectAnswer, question1.getCorrectAnswers().get(s));
            s++;
        }
        assertEquals(testGrammarSection, question1.getGrammarSection());
        assertEquals(testExercise, question1.getExercise());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testCreateQuestionObjectWithWrongPossibleAnswers() {
        Question question = new Question(testQuestionPhrase, testPossibleAnswersWrong, testCorrectAnswersRight, testGrammarSection, testExercise);
        assertEquals(testPossibleAnswer4, question.getPossibleAnswers().get(3));
        assertNull(question.getPossibleAnswers().get(4));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testCreateQuestionObjectWithWrongCorrectAnswers() {
        Question question = new Question(testQuestionPhrase, testPossibleAnswersRight, testCorrectAnswerWrong, testGrammarSection, testExercise);
        assertEquals(testCorrectAnswer4, (int) question.getCorrectAnswers().get(3));
        assertNull(question.getCorrectAnswers().get(4));
    }

    @Test
    public void testGetterAndSetter() {
        Question question = new Question(testQuestionPhrase, testPossibleAnswersRight, testCorrectAnswersRight, testGrammarSection, testExercise);
        question.setId(testId);
        question.setdBId(testDbId);
        assertEquals(testId, question.getId());
        assertEquals(testDbId, question.getdBId());
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testAddPossibleAnswer() {
        Question question = new Question();
        question.addPossibleAnswer(testPossibleAnswer1);
        question.addPossibleAnswer(testPossibleAnswer2);
        question.addPossibleAnswer(testPossibleAnswer3);
        question.addPossibleAnswer(testPossibleAnswer4);
        question.addPossibleAnswer(testPossibleAnswer5);
        assertEquals(testPossibleAnswer1, question.getPossibleAnswers().get(0));
        assertEquals(testPossibleAnswer2, question.getPossibleAnswers().get(1));
        assertEquals(testPossibleAnswer3, question.getPossibleAnswers().get(2));
        assertEquals(testPossibleAnswer4, question.getPossibleAnswers().get(3));
        assertNull(question.getPossibleAnswers().get(4));
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testAddCorrectAnswer() {
        Question question = new Question();
        question.addCorrectAnswer(testCorrectAnswer1);
        question.addCorrectAnswer(testCorrectAnswer2);
        question.addCorrectAnswer(testCorrectAnswer3);
        question.addCorrectAnswer(testCorrectAnswer4);
        question.addCorrectAnswer(testCorrectAnswer5);
        assertEquals(testCorrectAnswer1, (int) question.getCorrectAnswers().get(0));
        assertEquals(testCorrectAnswer2, (int) question.getCorrectAnswers().get(1));
        assertEquals(testCorrectAnswer3, (int) question.getCorrectAnswers().get(2));
        assertEquals(testCorrectAnswer4, (int) question.getCorrectAnswers().get(3));
        assertNull(question.getCorrectAnswers().get(4));
    }
}

