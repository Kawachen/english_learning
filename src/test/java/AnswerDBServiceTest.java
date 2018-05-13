import static org.junit.Assert.*;

import DBConnection.DBConnection;
import Services.Answer.AnswerDBInterface;
import Services.Answer.AnswerDBService;
import Utilities.Answer;
import Utilities.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDBServiceTest {

    private User user = new User("Kai", "Wachendoerfer", "kaiwach@web.de", "ddf", 667);
    private Answer answer = new Answer(1);
    private AnswerDBInterface answerDBService = new AnswerDBService();
    private Connection connection;

    public AnswerDBServiceTest() {
        DBConnection connectionPool = DBConnection.getInstance();
        this.connection = connectionPool.getConnection();
        answer.addChosenAnswer(0);
        answer.addChosenAnswer(2);
    }

    public static void deleteAllAnswers() {
        try {
            DBConnection connectionPool = DBConnection.getInstance();
            Connection connection = connectionPool.getConnection();
            PreparedStatement deleteAnswers = connection.prepareStatement("DELETE FROM result;");
            deleteAnswers.executeUpdate();
            PreparedStatement deleteChosenAnswers = connection.prepareStatement("DELETE FROM choosenansweres");
            deleteChosenAnswers.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Test
    public void testInsertAndSelectFromAnswer() {
        this.answerDBService.insertNewAnswerForUserIdInToDB(answer, user);
        ArrayList<Answer> answers = this.answerDBService.selectAnswersByUserEmailAddressFromDB(user.getEmailAddress());
        assertEquals(1, answers.size());
        assertEquals(answer.getQuestionId(), answers.get(0).getQuestionId());
        assertEquals(answer.getChosenAnswers().size(), answers.get(0).getChosenAnswers().size());
        assertEquals(answer.getChosenAnswers().get(0), answers.get(0).getChosenAnswers().get(0));
        assertEquals(answer.getChosenAnswers().get(1), answers.get(0).getChosenAnswers().get(1));
        deleteAllAnswers();
    }

    @Test
    public void testUpdateAndSelectTestWorkingTime() {
        long duration = 100;
        long newDuration = 50;
        int userId = 2;
        this.answerDBService.updateTestWorkingTimeByUserIdInToDB(duration, userId);
        long savedDuration = this.answerDBService.selectTestWorkingTimeByUserIdFromDB(userId);
        assertEquals(duration, savedDuration);
        this.answerDBService.updateTestWorkingTimeByUserIdInToDB(newDuration, userId);
        savedDuration = this.answerDBService.selectTestWorkingTimeByUserIdFromDB(userId);
        assertEquals(duration+newDuration, savedDuration);

        try {
            PreparedStatement deleteAnswers = this.connection.prepareStatement("DELETE FROM time");
            deleteAnswers.executeUpdate();
        } catch (SQLException e) {

        }
    }
}
