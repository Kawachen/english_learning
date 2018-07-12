package ServicesTests.QuestionTests;

import Datamodel.Question;
import Services.Question.QuestionDBInterface;
import Services.Question.QuestionDBService;
import TestUtils.TextFileReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

public class QuestionDBInterfaceTest {

    private String questionPhrase = "What if?";
    private String grammarSection = "Samplesection";
    private String exercise = "SampleExercise";
    private String possibleAnswer1 = "Sample1";
    private String possibleAnswer2 = "Sample2";
    private ArrayList<String> possibleAnswers = new ArrayList<String>() {
        {
            add(possibleAnswer1);
            add(possibleAnswer2);
        }
    };
    private int correctAnswer = 1;
    private ArrayList<Integer> correctAnswers = new ArrayList<Integer>() {
        {
            add(correctAnswer);
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

    @Test
    public void testSelectAllQuestionsFromDB() {
        TextFileReader textFileReader = new TextFileReader();
        ArrayList<Question> questions = textFileReader.readFileAndReturnQuestions();

        QuestionDBInterface questionDBService = new QuestionDBService();
        ArrayList<Question> questionsFromDB = questionDBService.selectAllQuestionsFromDB();

        compareTwoQuestionLists(questions, questionsFromDB);
    }

    @Test
    public void testInsertNewQuestionInToDB() {
        QuestionDBInterface questionDBService = new QuestionDBService();
        questionDBService.insertNewQuestionInToDB(question);
        ArrayList<Question> questions = questionDBService.selectAllQuestionsFromDB();
        Question questionFromDB = null;
        for(Question loopQuestion:questions) {
            if(loopQuestion.getQuestionPhrase().equals(question.getQuestionPhrase())) {
                questionFromDB = loopQuestion;
            }
        }
        compareTwoQuestions(question, questionFromDB);
        questionDBService.deleteQuestionByIdAndUpdateApplicationIds(questionFromDB.getdBId());
    }

    @Test
    public void testDeleteQuestionByIdAndUpdateApplicationIds() {
        QuestionDBInterface questionDBService = new QuestionDBService();
        questionDBService.insertNewQuestionInToDB(question);
        ArrayList<Question> questions = questionDBService.selectAllQuestionsFromDB();
        for(Question loopQuestion:questions) {
            if(loopQuestion.getQuestionPhrase().equals(question.getQuestionPhrase())) {
                question = loopQuestion;
            }
        }
        questionDBService.deleteQuestionByIdAndUpdateApplicationIds(question.getdBId());
        questions = questionDBService.selectAllQuestionsFromDB();
        boolean isQuestionStillInDB = false;
        int i = 1;
        for(Question loopQuestion:questions) {
            assertEquals(i, loopQuestion.getId());
            i++;
            if(loopQuestion.getQuestionPhrase().equals(question.getQuestionPhrase())) {
                isQuestionStillInDB = true;
            }
        }
        assertFalse(isQuestionStillInDB);
    }

    @Test
    public void testUpdateQuestionInDB() {
        QuestionDBInterface questionDBService = new QuestionDBService();
        questionDBService.insertNewQuestionInToDB(question);
        ArrayList<Question> questions = questionDBService.selectAllQuestionsFromDB();
        for(Question loopQuestion:questions) {
            if(loopQuestion.getQuestionPhrase().equals(question.getQuestionPhrase())) {
                question = loopQuestion;
            }
        }
        question.setQuestionPhrase(newQuestionPhrase);
        question.setGrammarSection(newGrammarSection);
        question.setExercise(newExercise);
        question.setPossibleAnswers(newPossibleAnswers);
        question.setCorrectAnswers(newCorrectAnswers);
        questionDBService.updateQuestionInDB(question);
        questions = questionDBService.selectAllQuestionsFromDB();
        Question newQuestionFromDB = null;
        for(Question loopQuestion:questions) {
            if(loopQuestion.getQuestionPhrase().equals(question.getQuestionPhrase())) {
                newQuestionFromDB = loopQuestion;
            }
        }
        compareTwoQuestions(newQuestion, newQuestionFromDB);
        questionDBService.deleteQuestionByIdAndUpdateApplicationIds(newQuestionFromDB.getdBId());
    }

    private void compareTwoQuestionLists(ArrayList<Question> questions1, ArrayList<Question> questions2) {
        for(int i = 0;i < Math.min(questions1.size(), questions2.size());i++) {
            assertEquals(questions1.get(i).getQuestionPhrase(), questions2.get(i).getQuestionPhrase());
            assertEquals(questions1.get(i).getGrammarSection(), questions2.get(i).getGrammarSection());
            assertEquals(questions1.get(i).getExercise(), questions2.get(i).getExercise());
            assertEquals(questions1.get(i).getPossibleAnswers().size(), questions2.get(i).getPossibleAnswers().size());
            assertEquals(questions1.get(i).getCorrectAnswers().size(), questions2.get(i).getCorrectAnswers().size());
            for(int j = 0;j<Math.min(questions1.get(i).getPossibleAnswers().size(), questions2.get(i).getPossibleAnswers().size());j++) {
                assertEquals(questions1.get(i).getPossibleAnswers().get(j), questions2.get(i).getPossibleAnswers().get(j));
            }
            for(int j = 0;j<Math.min(questions1.get(i).getCorrectAnswers().size(), questions2.get(i).getCorrectAnswers().size());j++) {
                assertEquals(questions1.get(i).getCorrectAnswers().get(j), questions2.get(i).getCorrectAnswers().get(j));
            }
        }
    }

    private void compareTwoQuestions(Question question1, Question question2) {
        assertEquals(question1.getQuestionPhrase(), question2.getQuestionPhrase());
        assertEquals(question1.getGrammarSection(), question2.getGrammarSection());
        assertEquals(question1.getExercise(), question2.getExercise());
        assertEquals(question1.getPossibleAnswers().size(), question2.getPossibleAnswers().size());
        assertEquals(question1.getCorrectAnswers().size(), question2.getCorrectAnswers().size());
        for(int j = 0;j<Math.min(question1.getPossibleAnswers().size(), question2.getPossibleAnswers().size());j++) {
            assertEquals(question1.getPossibleAnswers().get(j), question2.getPossibleAnswers().get(j));
        }
        for(int j = 0;j<Math.min(question1.getCorrectAnswers().size(), question2.getCorrectAnswers().size());j++) {
            assertEquals(question1.getCorrectAnswers().get(j), question2.getCorrectAnswers().get(j));
        }
    }
}
