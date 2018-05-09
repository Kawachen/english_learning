package Services.Question;

import Utilities.Question;

import java.util.ArrayList;

public class QuestionService {

    private QuestionDBInterface questionDBService = new QuestionDBService();
    private ArrayList<Question> questions;

    public QuestionService() {
        questions = questionDBService.selectAllQuestionsFromDB();
    }

    public ArrayList<Question> getAllQuestions() {
        return questions;
    }

    public int getCountOfQuestions() {
        return questions.size();
    }

    public Question getQuestionById(int questionId) {
        int questionIndex = questionId -1;
        if(questionIndex < questions.size() && questionIndex > - 1) {
            return questions.get(questionIndex);
        }
        return null;
    }

    public void setQuestionById(int questionId, Question question) {
        int questionIndex = questionId -1;
        this.questions.set(questionIndex, question);
    }

    public void setNewQuestionInToDB(Question question) {
        questionDBService.insertNewQuestionInToDB(question);
    }

    public void addNewQuestion(Question question) {
        this.questions.add(question);
    }

    public void updateQuestionInToDB(Question question) {
        questionDBService.updateQuestionInDB(question);
    }

    public void deleteQuestionByIdAndFromDB(int questionId) {
        int questionIndex = questionId -1;
        this.questions.remove(questionIndex);
        questionDBService.updateAllQuestionsInDB(questions);
    }
}
