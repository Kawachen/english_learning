package Services.Answer;

import DBConnection.DBConnection;
import Utilities.Answer;
import Utilities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDBService implements AnswerDBInterface {

    private DBConnection connectionPool = DBConnection.getInstance();

    public ArrayList<Answer> selectAnswersByUserEmailAddressFromDB(String emailAddress) {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        try {
            ResultSet dBData = selectAnswerByUserEmailAddress(emailAddress);
            answers = createAnswerArrayList(dBData);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: select answers by user email address from DB failed!");
        }
        return answers;
    }

    public void insertNewAnswerForUserIdInToDB(Answer answer, User user) {
        try {
            insertNewAnswersForUserId(answer, user);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: insert new answer for user id: into DB failed!");
        }
    }

    public void updateTestWorkingTimeByUserIdInToDB(long newDuration, int userId) {
        try {
            updateTestWorkingTimeByUserId(newDuration, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: update test working time by user id failed!");
        }
    }

    public long selectTestWorkingTimeByUserIdFromDB(int userId) {
        try {
            return selectTestWorkingTimeByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: select test working time by user id failed!");
        }
        return 0;
    }

    private ResultSet selectAnswerByUserEmailAddress(String emailAddress) throws SQLException {
        PreparedStatement preparedStatement = this.connectionPool.getConnection().prepareStatement("SELECT * FROM result LEFT JOIN chosenanswer ON result.id = chosenanswer.result_id WHERE result.user_email='"+emailAddress+"' ORDER BY result.id ASC;");
        ResultSet dBdata = preparedStatement.executeQuery();
        return dBdata;
    }

    private ArrayList<Answer> createAnswerArrayList(ResultSet resultSet) throws SQLException {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        int result_id_previous = 0;
        int chosenAnswers_id_previous = 0;
        Answer answer = new Answer();
        while (resultSet.next()) {
            if(resultSet.isFirst()) {
                result_id_previous = resultSet.getInt(1);
                answer.setQuestionId(resultSet.getInt("question_id"));
            } else if(result_id_previous < resultSet.getInt(1)) {
                answers.add(answer);
                answer = new Answer();
                result_id_previous = resultSet.getInt(1);
                answer.setQuestionId(resultSet.getInt("question_id"));
            }
            if(chosenAnswers_id_previous < resultSet.getInt(6)) {
                chosenAnswers_id_previous = resultSet.getInt(6);
                answer.addChosenAnswer(resultSet.getInt("chosenAnswer"));
            }
        }
        answers.add(answer);
        return answers;
    }

    private void insertNewAnswersForUserId(Answer answer, User user) throws SQLException {
        Connection connection = this.connectionPool.getConnection();
        PreparedStatement insertAnswer = connection.prepareStatement("INSERT INTO result (user_id, user_email, question_id) VALUES (?, ?, ?);");
        insertAnswer.setString(1, Integer.toString(user.getId()));
        insertAnswer.setString(2, user.getEmailAddress());
        insertAnswer.setString(3, Integer.toString(answer.getQuestionId()));
        insertAnswer.executeUpdate();
        PreparedStatement selectLastInsertedId = connection.prepareStatement("SELECT id FROM result WHERE id = LAST_INSERT_ID();");
        ResultSet resultSet = selectLastInsertedId.executeQuery();
        int resultId = 0;
        while (resultSet.next()) {
            resultId = resultSet.getInt("id");
        }
        for(int chosenAnswer: answer.getChosenAnswers()) {
            PreparedStatement insertChosenAnswersForLastInsertedId = connection.prepareStatement("INSERT INTO chosenanswer (result_id, chosenAnswer) VALUES (?, ?)");
            insertChosenAnswersForLastInsertedId.setString(1, Integer.toString(resultId));
            insertChosenAnswersForLastInsertedId.setString(2, Integer.toString(chosenAnswer));
            insertChosenAnswersForLastInsertedId.executeUpdate();
        }
    }

    private void updateTestWorkingTimeByUserId(long newDuration, int userId) throws SQLException {
        Connection connection = connectionPool.getConnection();
        PreparedStatement selectExistingTime = connection.prepareStatement("SELECT * FROM time WHERE user_id = ?;");
        selectExistingTime.setString(1, Integer.toString(userId));
        ResultSet resultSet = selectExistingTime.executeQuery();
        boolean result = false;
        long oldDuration = 0;
        while (resultSet.next()) {
            oldDuration = resultSet.getInt("time");
            result = true;
        }
        if(result) {
            PreparedStatement updateDuration = connection.prepareStatement("UPDATE time SET time = ? WHERE user_id = ?;");
            updateDuration.setString(1, Long.toString(newDuration + oldDuration));
            updateDuration.setString(2, Integer.toString(userId));
            updateDuration.executeUpdate();
        } else {
            PreparedStatement insertDuration = connection.prepareStatement("INSERT INTO time (user_id, time) VALUES (?, ?);");
            insertDuration.setString(1, Integer.toString(userId));
            insertDuration.setString(2, Long.toString(newDuration));
            insertDuration.executeUpdate();
        }
    }

    private long selectTestWorkingTimeByUserId(int userId) throws SQLException {
        PreparedStatement selectLastWorkingTime = this.connectionPool.getConnection().prepareStatement("SELECT * FROM time WHERE user_id = ?;");
        selectLastWorkingTime.setString(1, Integer.toString(userId));
        ResultSet resultSet = selectLastWorkingTime.executeQuery();
        while (resultSet.next()) {
            return resultSet.getLong("time");
        }
        return 0;
    }
}
