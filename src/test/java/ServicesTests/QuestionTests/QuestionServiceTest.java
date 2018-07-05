package ServicesTests.QuestionTests;

import Datamodel.Question;
import Services.Question.QuestionService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

public class QuestionServiceTest {

    private String questionPhrase = "How ________________ now? Better than before?  [2]";
    private String grammarSection = "Present and Past";
    private String exercise = "Study Unit 4";
    private String possibleAnswer1 = "you are feeling";
    private String possibleAnswer2 = "do you feel";
    private String possibleAnswer3 = "are you feeling";
    private ArrayList<String> possibleAnswers = new ArrayList<String>() {
        {
            add(possibleAnswer1);
            add(possibleAnswer2);
            add(possibleAnswer3);
        }
    };
    private int correctAnswer1 = 1;
    private int correctAnswer2 = 2;
    private ArrayList<Integer> correctAnswers = new ArrayList<Integer>() {
        {
            add(correctAnswer1);
            add(correctAnswer2);
        }
    };
    private Question question = new Question(questionPhrase, possibleAnswers, correctAnswers, grammarSection, exercise);

    private String newQuestionPhrase = "What else?";
    private String newGrammarSection = "Samplesection2";
    private String newExercise = "SamppleExercise2";
    private String newPossibleAnswer1 = "newSample1";
    private String newPossibleAnswer2 = "newSample2";
    private ArrayList<String> newPossibleAnswers = new ArrayList<String>() {
        {
            add(newPossibleAnswer1);
            add(newPossibleAnswer2);
        }
    };
    private int newCorrectAnswer = 1;
    private ArrayList<Integer> newCorrectAnswers = new ArrayList<Integer>() {
        {
            add(newCorrectAnswer);
        }
    };
    private Question newQuestion = new Question(newQuestionPhrase, newPossibleAnswers, newCorrectAnswers, newGrammarSection, newExercise);

    private String new2QuestionPhrase = "What if?";
    private String new2GrammarSection = "Samplesection";
    private String new2Exercise = "SampleExercise";
    private String new2PossibleAnswer1 = "Sample1";
    private String new2PossibleAnswer2 = "Sample2";
    private ArrayList<String> new2PossibleAnswers = new ArrayList<String>() {
        {
            add(new2PossibleAnswer1);
            add(new2PossibleAnswer2);
        }
    };
    private int correctAnswer = 1;
    private ArrayList<Integer> new2CorrectAnswers = new ArrayList<Integer>() {
        {
            add(correctAnswer);
        }
    };
    private Question new2Question = new Question(new2QuestionPhrase, new2PossibleAnswers, new2CorrectAnswers, new2GrammarSection, new2Exercise);


    @Test
    public void testCreateQuestionServiceAndTestAllGetter() {
        QuestionService questionService = new QuestionService();
        assertEquals(160, questionService.getCountOfQuestions());
        ArrayList<Question> questions = questionService.getAllQuestions();
        int i = 0;
        for(Question question:questions) {
            i++;
        }
        assertEquals(160, i);
        Question question4 = questionService.getQuestionById(4);
        compareTwoQuestions(question, question4);
    }

    @Test
    public void testSetQuestionById() {
        QuestionService questionService = new QuestionService();
        questionService.setQuestionById(3 ,question);
        Question question3 = questionService.getQuestionById(3);
        compareTwoQuestions(question, question3);
    }

    @Test
    public void testSetAndDeleteQuestionInToDB() {
        QuestionService questionService = new QuestionService();
        questionService.setNewQuestionInToDB(newQuestion);
        QuestionService newQuestionService = new QuestionService();
        Question newQuestionFromDB = newQuestionService.getQuestionById(161);
        compareTwoQuestions(newQuestion, newQuestionFromDB);
        newQuestionService.deleteQuestionByIdAndUpdateDB(newQuestionFromDB.getId());
        QuestionService newQuestionService2 = new QuestionService();
        ArrayList<Question> questions = newQuestionService2.getAllQuestions();
        boolean isQuestionInList = false;
        for(Question loopQuestion: questions) {
            if(loopQuestion.getId() == newQuestionFromDB.getId()) {
                isQuestionInList = true;
            }
        }
        assertFalse(isQuestionInList);
    }

    @Test
    public void testUpdateQuestionInToDB() {
        QuestionService questionService = new QuestionService();
        questionService.setNewQuestionInToDB(newQuestion);
        QuestionService newQuestionService = new QuestionService();
        newQuestion = newQuestionService.getQuestionById(161);
        new2Question.setdBId(newQuestion.getdBId());
        newQuestionService.updateQuestionInToDB(new2Question);
        QuestionService new2QuestionService = new QuestionService();
        Question newQuestionFromDB = new2QuestionService.getQuestionById(161);
        compareTwoQuestions(new2Question, newQuestionFromDB);
        new2QuestionService.deleteQuestionByIdAndUpdateDB(newQuestionFromDB.getId());
    }

    @Test
    public void testAddNewQuestion() {
        QuestionService questionService = new QuestionService();
        questionService.addNewQuestion(newQuestion);
        ArrayList<Question> questions = questionService.getAllQuestions();
        boolean doesQuestionExists = false;
        for(Question loopQuestion: questions) {
            if(loopQuestion.getQuestionPhrase().equals(newQuestion.getQuestionPhrase())) {
                doesQuestionExists = true;
            }
        }
        assertTrue(doesQuestionExists);
    }

    private void compareTwoQuestions(Question question1, Question question2) {
        assertEquals(question1.getQuestionPhrase(), question2.getQuestionPhrase());
        assertEquals(question1.getGrammarSection(), question2.getGrammarSection());
        assertEquals(question1.getExercise(), question2.getExercise());
        for(int j = 0;j<Math.min(question1.getPossibleAnswers().size(), question2.getPossibleAnswers().size());j++) {
            assertEquals(question1.getPossibleAnswers().get(j), question2.getPossibleAnswers().get(j));
        }
        for(int j = 0;j<Math.min(question1.getCorrectAnswers().size(), question2.getCorrectAnswers().size());j++) {
            assertEquals(question1.getCorrectAnswers().get(j), question2.getCorrectAnswers().get(j));
        }
    }
}
