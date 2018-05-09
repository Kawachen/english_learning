package Services.Question;

import Utilities.Question;

import java.util.ArrayList;

public interface QuestionDBInterface {

    ArrayList<Question> selectAllQuestionsFromDB();

    void insertNewQuestionInToDB(Question question);

    void updateQuestionInDB(Question question);

    void updateAllQuestionsInDB(ArrayList<Question> questions);
}
