package ServicesTests.AnswerTests;

import DBConnection.DBConnection;
import Datamodel.Answer;
import Datamodel.User;
import Enums.Role;
import Services.Answer.AnswerDBInterface;
import Services.Answer.AnswerDBService;
import Services.User.UserService;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AnswerDBInterfaceTest {

    private int testQuestionId = 4;
    private int testChosenAnswer1 = 2;
    private int testChosenAnswer2 = 3;
    private int testChosenAnswer3 = 0;
    private Answer answer = new Answer();

    private String testFirstname = "Rudolf";
    private String testLastname = "Peterson";
    private String testEmail = "rudolf@gmail.com";
    private String testPassword = "passwort";
    private int testSalt = 42;
    private Role testRole = Role.STUDENT;
    private User user = new User(testFirstname, testLastname, testEmail, testPassword, testSalt);

    private long workingDuration1 = 50;
    private long workingDuration2 = 199;


    public AnswerDBInterfaceTest() {
        answer.setQuestionId(testQuestionId);
        answer.addChosenAnswer(testChosenAnswer1);
        answer.addChosenAnswer(testChosenAnswer2);
        answer.addChosenAnswer(testChosenAnswer3);

        user.setRole(testRole);
    }

    @Test
    public void testInsertNewAnswerAndSelectAnswersByUserID() {
        AnswerDBInterface answerDBService = new AnswerDBService();
        UserService userService = new UserService();
        userService.addNewUser(user);
        user = userService.getUserByEmailAddress(user.getEmailAddress());
        answerDBService.insertNewAnswerForUserIdInToDB(answer, user);
        ArrayList<Answer> answers = answerDBService.selectAnswersByUserEmailAddressFromDB(user.getEmailAddress());
        Answer localAnswer = null;
        for(Answer loopAnswer:answers) {
            if(loopAnswer.getQuestionId() == answer.getQuestionId()) {
                localAnswer = loopAnswer;
            }
        }
        assertEquals(testQuestionId, localAnswer.getQuestionId());
        assertEquals(testChosenAnswer1, (int) localAnswer.getChosenAnswers().get(0));
        assertEquals(testChosenAnswer2, (int) localAnswer.getChosenAnswers().get(1));
        assertEquals(testChosenAnswer3, (int) localAnswer.getChosenAnswers().get(2));
        deleteAllAnswersByAnswerID(localAnswer.getId());
        userService.deleteUserById(user.getId());
    }

    @Test
    public void testSelectAndAddWorkingTime() {
        AnswerDBInterface answerDBService = new AnswerDBService();
        UserService userService = new UserService();
        userService.addNewUser(user);
        user = userService.getUserByEmailAddress(user.getEmailAddress());
        answerDBService.updateTestWorkingTimeByUserIdInToDB(workingDuration1, user.getId());
        assertEquals(workingDuration1, answerDBService.selectTestWorkingTimeByUserIdFromDB(user.getId()));
        answerDBService.updateTestWorkingTimeByUserIdInToDB(workingDuration2, user.getId());
        assertEquals(workingDuration1+workingDuration2, answerDBService.selectTestWorkingTimeByUserIdFromDB(user.getId()));
        deleteTestWorkingTimeByUserId(user.getId());
        userService.deleteUserById(user.getId());
    }

    protected void deleteAllAnswersByAnswerID(int answerId) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement deleteAllAnswersByAnswerId = connection.prepareStatement("DELETE FROM result WHERE id = ?;");
            deleteAllAnswersByAnswerId.setString(1, Integer.toString(answerId));
            deleteAllAnswersByAnswerId.executeUpdate();
            PreparedStatement deleteAllChosenAnswersByAnswerId = connection.prepareStatement("DELETE FROM chosenanswer WHERE result_id = ?;");
            deleteAllChosenAnswersByAnswerId.setString(1, Integer.toString(answerId));
            deleteAllChosenAnswersByAnswerId.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleting all answers failed!");
            e.printStackTrace();
        }
    }

    private void deleteTestWorkingTimeByUserId(int userId) {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement deleteTestWorkingTime = connection.prepareStatement("DELETE FROM time WHERE user_id = ?;");
            deleteTestWorkingTime.setString(1, Integer.toString(userId));
            deleteTestWorkingTime.executeUpdate();
        } catch (SQLException e) {
            System.out.println("deleting test working time failed!");
            e.printStackTrace();
        }
    }
}
