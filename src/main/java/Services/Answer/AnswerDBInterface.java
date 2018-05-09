package Services.Answer;

import Utilities.Answer;
import Utilities.User;

import java.util.ArrayList;

public interface AnswerDBInterface {

    ArrayList<Answer> selectAnswersByUserEmailAddressFromDB(String emailAddress);

    void insertNewAnswerForUserIdInToDB(Answer answer, User user);

    void updateTestWorkingTimeByUserIdInToDB(long newDuration, int userId);

    long selectTestWorkingTimeByUserIdFromDB(int userId);


}
