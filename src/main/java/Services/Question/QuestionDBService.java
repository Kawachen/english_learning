package Services.Question;

import DBConnection.DBConnection;
import Datamodel.Question;

import java.sql.*;
import java.util.ArrayList;

public class QuestionDBService implements QuestionDBInterface {

    private Connection dBConnection;

    public QuestionDBService() {
        DBConnection connectionPool = DBConnection.getInstance();
        dBConnection = connectionPool.getConnection();
    }

    public ArrayList<Question> selectAllQuestionsFromDB() {
        ArrayList<Question> questions = new ArrayList<>();
        try {
            questions = selectAllQuestions();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: select all questions from DB failed!");
        }
        return questions;
    }

    public void insertNewQuestionInToDB(Question question) {
        try {
            insertNewQuestion(question);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: ");
        }
    }

    public void updateQuestionInDB(Question question) {
        try {
            updateQuestion(question);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: update question in DB failed!");
        }
    }

    public void deleteQuestionByIdAndUpdateApplicationIds(int dBQuestionId) {
        try {
            deleteQuestionById(dBQuestionId);
            updateApplicationIds(dBQuestionId);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: delete question and update application ids failed!");
        }
    }

    private void deleteQuestionById(int dBQuestionId)throws SQLException {
        String dBId = Integer.toString(dBQuestionId);
        PreparedStatement deleteQuestion = dBConnection.prepareStatement("DELETE FROM question WHERE id = ?;");
        deleteQuestion.setString(1, dBId);
        deleteQuestion.executeUpdate();
        PreparedStatement deletePossibleAnswers = dBConnection.prepareStatement("DELETE FROM possibleanswer WHERE question_id = ?;");
        deletePossibleAnswers.setString(1, dBId);
        PreparedStatement deleteCorrectAnswers = dBConnection.prepareStatement("DELETE FROM correctanswer WHERE question_id = ?;");
        deleteCorrectAnswers.setString(1, dBId);
        deleteCorrectAnswers.executeUpdate();
    }

    private void updateApplicationIds(int dBQuestionId) throws SQLException {
        PreparedStatement selectQuestions = dBConnection.prepareStatement("SELECT id, applicationId FROM question WHERE id > ? ORDER BY id ASC;");
        selectQuestions.setString(1, Integer.toString(dBQuestionId));
        ResultSet resultSet = selectQuestions.executeQuery();
        while(resultSet.next()) {
            PreparedStatement updateApplicationId = dBConnection.prepareStatement("UPDATE question SET applicationId = ? WHERE id = ?;");
            updateApplicationId.setString(1, Integer.toString(resultSet.getInt("applicationId")-1));
            updateApplicationId.setString(2, resultSet.getString("id"));
            updateApplicationId.executeUpdate();
        }
    }

    private ArrayList<Question> selectAllQuestions() throws SQLException {
        ArrayList<Question> questions;
        questions = selectIdQuestionGrammarAndExercise();
        questions = selectPossibleAndCorrectAnswersForEachQuestion(questions);
        return questions;
    }

    private ArrayList<Question> selectIdQuestionGrammarAndExercise() throws SQLException {
        ArrayList<Question> questions = new ArrayList<>();
        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT id, applicationId, questionPhrase, grammarSection, exercise FROM question ORDER BY id ASC;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Question question = new Question();
            question.setdBId(resultSet.getInt("id"));
            question.setId(resultSet.getInt("applicationId"));
            question.setQuestionPhrase(resultSet.getString("questionPhrase"));
            question.setGrammarSection(resultSet.getString("grammarSection"));
            question.setExercise(resultSet.getString("exercise"));
            questions.add(question);
        }
        return questions;
    }

    private ArrayList<Question> selectPossibleAndCorrectAnswersForEachQuestion(ArrayList<Question> questions) throws SQLException {
        for(int i=0;i<questions.size();i++) {
            PreparedStatement preparedStatementGetPossibleAnswers = dBConnection.prepareStatement("SELECT * FROM possibleanswer WHERE question_id = ? ORDER BY id ASC");
            preparedStatementGetPossibleAnswers.setString(1, Integer.toString(questions.get(i).getdBId()));
            ResultSet resultSetGetPossibleAnswers = preparedStatementGetPossibleAnswers.executeQuery();
            while(resultSetGetPossibleAnswers.next()) {
                questions.get(i).addPossibleAnswer(resultSetGetPossibleAnswers.getString("possibleAnswer"));
            }
            PreparedStatement preparedStatementGetCorrectAnswers = dBConnection.prepareStatement("SELECT * FROM correctanswer WHERE question_id = ? ORDER BY id ASC");
            preparedStatementGetCorrectAnswers.setString(1, Integer.toString(questions.get(i).getdBId()));
            ResultSet resultSetGetCorrectAnswers = preparedStatementGetCorrectAnswers.executeQuery();
            while(resultSetGetCorrectAnswers.next()) {
                questions.get(i).addCorrectAnswer(resultSetGetCorrectAnswers.getInt("correctAnswer"));
            }
        }
        return questions;
    }

    private void updateQuestion(Question question) throws SQLException {
        updateQuestionGrammarAndExerciseById(question);
        updatePossibleAndCorrectAnswers(question);
    }

    private void updateQuestionGrammarAndExerciseById(Question question) throws SQLException{
        PreparedStatement preparedStatementQuestion = dBConnection.prepareStatement("UPDATE question SET questionPhrase = ?, grammarSection = ?, exercise = ? WHERE id = ?;");
        preparedStatementQuestion.setString(1, question.getQuestionPhrase());
        preparedStatementQuestion.setString(2, question.getGrammarSection());
        preparedStatementQuestion.setString(3, question.getExercise());
        preparedStatementQuestion.setString(4, Integer.toString(question.getdBId()));
        preparedStatementQuestion.executeUpdate();
    }

    private void updatePossibleAndCorrectAnswers(Question question) throws SQLException {
        PreparedStatement preparedStatementDeleteOldPossibleAnswer = dBConnection.prepareStatement("DELETE FROM possibleanswer WHERE question_id = ?;");
        preparedStatementDeleteOldPossibleAnswer.setString(1, Integer.toString(question.getdBId()));
        preparedStatementDeleteOldPossibleAnswer.executeUpdate();

        PreparedStatement preparedStatementDeleteOldCorrectAnswer = dBConnection.prepareStatement("DELETE FROM correctanswer WHERE question_id = ?;");
        preparedStatementDeleteOldCorrectAnswer.setString(1, Integer.toString(question.getdBId()));
        preparedStatementDeleteOldCorrectAnswer.executeUpdate();

        for(String possibleAnswer: question.getPossibleAnswers()) {
            PreparedStatement preparedStatementPossibleAnswer = dBConnection.prepareStatement("INSERT INTO possibleAnswer (question_id, possibleAnswer) VALUES (?, ?);");
            preparedStatementPossibleAnswer.setString(1, Integer.toString(question.getdBId()));
            preparedStatementPossibleAnswer.setString(2, possibleAnswer);
            preparedStatementPossibleAnswer.executeUpdate();
        }
        for(Integer correctAnswer: question.getCorrectAnswers()) {
            PreparedStatement preparedStatementCorrectAnswer = dBConnection.prepareStatement("INSERT INTO correctanswer (question_id, correctAnswer) VALUES (?, ?);");
            preparedStatementCorrectAnswer.setString(1, Integer.toString(question.getdBId()));
            preparedStatementCorrectAnswer.setString(2, Integer.toString(correctAnswer));
            preparedStatementCorrectAnswer.executeUpdate();
        }
    }

    private void insertNewQuestion(Question questionWithoutId) throws SQLException {
        int lastQuestionId = selectLastQuestionIdFromDB();
        questionWithoutId.setId(lastQuestionId+1);
        Question questionWithId = questionWithoutId;
        insertNewQuestionWithId(questionWithId);
    }

    private void insertNewQuestionWithId(Question question) throws SQLException {
        PreparedStatement preparedStatementQuestion = dBConnection.prepareStatement("INSERT INTO question (applicationId, questionPhrase, grammarSection, exercise) VALUE (?, ?, ?, ?);");
        preparedStatementQuestion.setString(1, Integer.toString(question.getId()));
        preparedStatementQuestion.setString(2, question.getQuestionPhrase());
        preparedStatementQuestion.setString(3, question.getGrammarSection());
        preparedStatementQuestion.setString(4,question.getExercise());
        preparedStatementQuestion.executeUpdate();

        int lastInsertedId = getLastInsertedIdFromQuestionTable();

        for(String possibleAnswer: question.getPossibleAnswers()) {
            PreparedStatement preparedStatementPossibleAnswer = dBConnection.prepareStatement("INSERT INTO possibleAnswer (question_id, possibleAnswer) VALUES (?, ?)");
            preparedStatementPossibleAnswer.setString(1, Integer.toString(lastInsertedId));
            preparedStatementPossibleAnswer.setString(2, possibleAnswer);
            preparedStatementPossibleAnswer.executeUpdate();
        }
        for(Integer correctAnswer: question.getCorrectAnswers()) {
            PreparedStatement preparedStatementCorrectAnswer = dBConnection.prepareStatement("INSERT INTO correctanswer (question_id, correctAnswer) VALUES (?, ?)");
            preparedStatementCorrectAnswer.setString(1, Integer.toString(lastInsertedId));
            preparedStatementCorrectAnswer.setString(2, Integer.toString(correctAnswer));
            preparedStatementCorrectAnswer.executeUpdate();
        }
    }

    private int getLastInsertedIdFromQuestionTable() throws SQLException {
        PreparedStatement selectLastInsertedId = dBConnection.prepareStatement("SELECT id FROM question WHERE id = LAST_INSERT_ID();");
        ResultSet resultSet = selectLastInsertedId.executeQuery();
        int resultId = 0;
        while (resultSet.next()) {
            resultId = resultSet.getInt("id");
        }
        return resultId;
    }

    private int selectLastQuestionIdFromDB() {
        try {
            return selectLastQuestionId();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: select last question id from DB failed!");
        }
        return 0;
    }

    private int selectLastQuestionId() throws SQLException {
        int questionId = 0;
        PreparedStatement selectAllQuestions = dBConnection.prepareStatement("SELECT applicationId FROM question;");
        ResultSet idSet = selectAllQuestions.executeQuery();
        while(idSet.next()) {
            questionId = idSet.getInt("applicationId");
        }
        return questionId;
    }
}
