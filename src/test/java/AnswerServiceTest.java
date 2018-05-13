import static org.junit.Assert.*;

import DBConnection.DBConnection;
import Services.Answer.AnswerService;
import Utilities.Answer;
import Utilities.User;
import org.junit.Test;

import java.sql.Connection;

public class AnswerServiceTest {

    private User user = new User("Kai", "Wachendoerfer", "kaiwach@web.de", "ddf", 667);
    private AnswerService answerService = new AnswerService(user);
    private Answer answer = new Answer(1);
    private Connection connection;

    public AnswerServiceTest() {
        DBConnection connectionPool = DBConnection.getInstance();
        this.connection = connectionPool.getConnection();
        answer.addChosenAnswer(0);
        answer.addChosenAnswer(2);
    }

    @Test
    public void testGetLatestAnswerQuestionIdByUserEmail() {
        assertEquals(1, this.answerService.getLatestAnswerQuestionId());
        this.answerService.addNewAnswer(answer);
        assertEquals(2, this.answerService.getLatestAnswerQuestionId());
        AnswerDBServiceTest.deleteAllAnswers();
    }
}
