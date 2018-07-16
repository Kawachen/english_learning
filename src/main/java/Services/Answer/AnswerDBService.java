package Services.Answer;

import DBConnection.DBConnection;
import Datamodel.Answer;
import Datamodel.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDBService implements AnswerDBInterface {

    private Connection dBConnection;

    public AnswerDBService() {
        DBConnection connectionPool = DBConnection.getInstance();
        dBConnection = connectionPool.getConnection();
    }

    public ArrayList<Answer> selectAnswersByUserEmailAddressFromDB(String emailAddress) {
        ArrayList<Answer> answers = new ArrayList<Answer>();
        try {
            answers = selectAnswerByUserEmailAddress(emailAddress);
            answers = selectChosenCorrectAndPossibleAnswerForEachAnswer(answers);
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

    private ArrayList<Answer> selectAnswerByUserEmailAddress(String emailAddress) throws SQLException {
        ArrayList<Answer> answers = new ArrayList<>();
        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT * FROM result WHERE user_email= ? ORDER BY id ASC;");
        preparedStatement.setString(1, emailAddress);
        ResultSet dBdata = preparedStatement.executeQuery();
        while(dBdata.next()) {
            Answer answer = new Answer(dBdata.getInt("id"), dBdata.getInt("question_id"), dBdata.getString("questionphrase"), dBdata.getString("grammarsection"), dBdata.getString("exercise"));
            answers.add(answer);
        }
        return answers;
    }

    private ArrayList<Answer> selectChosenCorrectAndPossibleAnswerForEachAnswer(ArrayList<Answer> answers) throws SQLException {
        for(int i = 0; i < answers.size();i++) {
            PreparedStatement preparedStatementGetPossibleAnswers = dBConnection.prepareStatement("SELECT * FROM userversionedpossibleanswers WHERE result_id = ? ORDER BY id ASC");
            preparedStatementGetPossibleAnswers.setString(1, Integer.toString(answers.get(i).getId()));
            ResultSet resultSetGetPossibleAnswers = preparedStatementGetPossibleAnswers.executeQuery();
            while(resultSetGetPossibleAnswers.next()) {
                answers.get(i).addPossibleAnswer(resultSetGetPossibleAnswers.getString("possibleAnswer"));
            }
            PreparedStatement preparedStatementGetCorrectAnswers = dBConnection.prepareStatement("SELECT * FROM userversionedcorrectanswers WHERE result_id = ? ORDER BY id ASC");
            preparedStatementGetCorrectAnswers.setString(1, Integer.toString(answers.get(i).getId()));
            ResultSet resultSetGetCorrectAnswers = preparedStatementGetCorrectAnswers.executeQuery();
            while(resultSetGetCorrectAnswers.next()) {
                answers.get(i).addCorrectAnswer(resultSetGetCorrectAnswers.getInt("correctAnswer"));
            }
            PreparedStatement preparedStatementGetChosenAnswers = dBConnection.prepareStatement("SELECT * FROM chosenanswer WHERE result_id = ? ORDER BY id ASC");
            preparedStatementGetChosenAnswers.setString(1, Integer.toString(answers.get(i).getId()));
            ResultSet resultSetGetChosenAnswers = preparedStatementGetChosenAnswers.executeQuery();
            while(resultSetGetChosenAnswers.next()) {
                answers.get(i).addChosenAnswer(resultSetGetChosenAnswers.getInt("chosenAnswer"));
            }
        }
        return answers;
    }

    private void insertNewAnswersForUserId(Answer answer, User user) throws SQLException {
        PreparedStatement insertAnswer = dBConnection.prepareStatement("INSERT INTO result (user_id, user_email, question_id, questionphrase, grammarsection, exercise) VALUES (?, ?, ?, ?, ?, ?);");
        insertAnswer.setString(1, Integer.toString(user.getId()));
        insertAnswer.setString(2, user.getEmailAddress());
        insertAnswer.setString(3, Integer.toString(answer.getQuestionId()));
        insertAnswer.setString(4, answer.getQuestionPhrase());
        insertAnswer.setString(5, answer.getGrammarSection());
        insertAnswer.setString(6, answer.getExercise());
        insertAnswer.executeUpdate();
        PreparedStatement selectLastInsertedId = dBConnection.prepareStatement("SELECT id FROM result WHERE id = LAST_INSERT_ID();");
        ResultSet resultSet = selectLastInsertedId.executeQuery();
        int resultId = 0;
        while (resultSet.next()) {
            resultId = resultSet.getInt("id");
        }
        for(int chosenAnswer: answer.getChosenAnswers()) {
            PreparedStatement insertChosenAnswersForLastInsertedId = dBConnection.prepareStatement("INSERT INTO chosenanswer (result_id, chosenAnswer) VALUES (?, ?)");
            insertChosenAnswersForLastInsertedId.setString(1, Integer.toString(resultId));
            insertChosenAnswersForLastInsertedId.setString(2, Integer.toString(chosenAnswer));
            insertChosenAnswersForLastInsertedId.executeUpdate();
        }
        for(int correctAnswer: answer.getCorrectAnswers()) {
            PreparedStatement insertCorrectAnswersForLastInsertedId = dBConnection.prepareStatement("INSERT INTO userversionedcorrectanswers (result_id, correctanswer) VALUES (?, ?)");
            insertCorrectAnswersForLastInsertedId.setString(1, Integer.toString(resultId));
            insertCorrectAnswersForLastInsertedId.setString(2, Integer.toString(correctAnswer));
            insertCorrectAnswersForLastInsertedId.executeUpdate();
        }
        for(String possibleAnswer: answer.getPossibleAnswers()) {
            PreparedStatement insertPossibleAnswersForLastInsertedId = dBConnection.prepareStatement( "INSERT INTO userversionedpossibleanswers (result_id, possibleanswer) VALUES (?, ?)");
            insertPossibleAnswersForLastInsertedId.setString(1, Integer.toString(resultId));
            insertPossibleAnswersForLastInsertedId.setString(2, possibleAnswer);
            insertPossibleAnswersForLastInsertedId.executeUpdate();
        }
    }

    private void updateTestWorkingTimeByUserId(long newDuration, int userId) throws SQLException {
        PreparedStatement selectExistingTime = dBConnection.prepareStatement("SELECT * FROM time WHERE user_id = ?;");
        selectExistingTime.setString(1, Integer.toString(userId));
        ResultSet resultSet = selectExistingTime.executeQuery();
        boolean result = false;
        long oldDuration = 0;
        while (resultSet.next()) {
            oldDuration = resultSet.getInt("time");
            result = true;
        }
        if(result) {
            PreparedStatement updateDuration = dBConnection.prepareStatement("UPDATE time SET time = ? WHERE user_id = ?;");
            updateDuration.setString(1, Long.toString(newDuration + oldDuration));
            updateDuration.setString(2, Integer.toString(userId));
            updateDuration.executeUpdate();
        } else {
            PreparedStatement insertDuration = dBConnection.prepareStatement("INSERT INTO time (user_id, time) VALUES (?, ?);");
            insertDuration.setString(1, Integer.toString(userId));
            insertDuration.setString(2, Long.toString(newDuration));
            insertDuration.executeUpdate();
        }
    }

    private long selectTestWorkingTimeByUserId(int userId) throws SQLException {
        PreparedStatement selectLastWorkingTime = dBConnection.prepareStatement("SELECT * FROM time WHERE user_id = ?;");
        selectLastWorkingTime.setString(1, Integer.toString(userId));
        ResultSet resultSet = selectLastWorkingTime.executeQuery();
        while (resultSet.next()) {
            return resultSet.getLong("time");
        }
        return 0;
    }
}
