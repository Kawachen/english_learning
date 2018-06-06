package Services.Question;

import Datamodel.Question;

import java.util.ArrayList;

public interface QuestionDBInterface {

    ArrayList<Question> selectAllQuestionsFromDB();

    void insertNewQuestionInToDB(Question question);

    void updateQuestionInDB(Question question);

    void deleteQuestionByIdAndUpdateApplicationIds(int dBQuestionId);
}
