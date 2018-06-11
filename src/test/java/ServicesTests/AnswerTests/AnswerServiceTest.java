package ServicesTests.AnswerTests;

import Datamodel.Answer;
import Datamodel.User;
import Enums.Role;
import Services.Answer.AnswerService;
import Services.User.UserService;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AnswerServiceTest {

    private UserService userService = new UserService();

    private String testFirstname = "Rudolf";
    private String testLastname = "Peterson";
    private String testEmail = "rudolf@gmail.com";
    private String testPassword = "passwort";
    private int testSalt = 42;
    private Role testRole = Role.STUDENT;
    private User user = new User(testFirstname, testLastname, testEmail, testPassword, testSalt);

    private int testQuestionId = 4;
    private int testChosenAnswer1 = 2;
    private int testChosenAnswer2 = 3;
    private int testChosenAnswer3 = 0;
    private Answer answer = new Answer();

    private Answer answer2 = new Answer() {
        {
            setQuestionId(34);
            addChosenAnswer(2);
        }
    };

    private long workingDuration1 = 50;
    private long workingDuration2 = 199;

    public AnswerServiceTest() {
        answer.setQuestionId(testQuestionId);
        answer.addChosenAnswer(testChosenAnswer1);
        answer.addChosenAnswer(testChosenAnswer2);
        answer.addChosenAnswer(testChosenAnswer3);

        user.setRole(testRole);
    }

    @Test
    public void testCreateAnswerServiceObject() {
        userService.addNewUser(user);
        user = userService.getUserByEmailAddress(user.getEmailAddress());
        AnswerService answerService = new AnswerService(user);
        assertEquals(AnswerService.class, answerService.getClass());
        userService.deleteUserById(user.getId());
    }

    @Test
    public void testAddNewAnswerAndGetAnswerByQuestionIdAndGetAllAnswers() {
        userService.addNewUser(user);
        user = userService.getUserByEmailAddress(user.getEmailAddress());
        AnswerService answerService = new AnswerService(user);
        answerService.addNewAnswer(answer);
        Answer localAnswer = answerService.getAnswerByQuestionId(answer.getQuestionId());
        compareAnswerObjectWithInitData(localAnswer);
        answerService.addNewAnswer(answer);
        assertEquals(1, answerService.getAnswers().size());
        answerService.addNewAnswer(answer2);
        ArrayList<Answer> answers = answerService.getAnswers();
        compareAnswerObjectWithInitData(answers.get(0));
        assertEquals(2, answers.size());
        userService.deleteUserById(user.getId());
    }

    @Test
    public void testSaveAnswers() {
        userService.addNewUser(user);
        user = userService.getUserByEmailAddress(user.getEmailAddress());
        AnswerService answerService = new AnswerService(user);
        answerService.addNewAnswer(answer);
        answerService.saveNewAnswers();
        AnswerService answerService2 = new AnswerService(user);
        Answer localAnswer = answerService2.getAnswerByQuestionId(answer.getQuestionId());
        assertNotNull(localAnswer);
        compareAnswerObjectWithInitData(localAnswer);
        AnswerDBInterfaceTest answerDBInterfaceTest = new AnswerDBInterfaceTest();
        answerDBInterfaceTest.deleteAllAnswersByAnswerID(localAnswer.getId());
        userService.deleteUserById(user.getId());
    }

    @Test
    public void testGetLatestAnswerQuestionId() {
        userService.addNewUser(user);
        user = userService.getUserByEmailAddress(user.getEmailAddress());
        AnswerService answerService = new AnswerService(user);
        assertEquals(1, answerService.getFirstNewAnswerQuestionId());
        answerService.addNewAnswer(answer);
        assertEquals(5, answerService.getFirstNewAnswerQuestionId());
        userService.deleteUserById(user.getId());
    }

    @Test
    public void testUpdateAndGetTestWorkingTime() {
        userService.addNewUser(user);
        user = userService.getUserByEmailAddress(user.getEmailAddress());
        AnswerService answerService = new AnswerService(user);
        answerService.updateTestWorkingTimeByUserId(workingDuration1, user.getId());
        assertEquals(workingDuration1, answerService.getTestWorkingTimeByUserId(user.getId()));
        answerService.updateTestWorkingTimeByUserId(workingDuration2, user.getId());
        assertEquals(workingDuration1+workingDuration2, answerService.getTestWorkingTimeByUserId(user.getId()));
        userService.deleteUserById(user.getId());
    }

    private void compareAnswerObjectWithInitData(Answer answer) {
        assertEquals(testQuestionId, answer.getQuestionId());
        assertEquals(testChosenAnswer1, (int) answer.getChosenAnswers().get(0));
        assertEquals(testChosenAnswer2, (int) answer.getChosenAnswers().get(1));
        assertEquals(testChosenAnswer3, (int) answer.getChosenAnswers().get(2));
    }
}
