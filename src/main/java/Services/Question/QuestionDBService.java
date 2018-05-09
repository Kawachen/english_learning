package Services.Question;

import DBConnection.DBConnection;
import Utilities.Question;

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

    public void updateAllQuestionsInDB(ArrayList<Question> questions) {
        try {
            updateAllQuestions(questions);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR: update all questions in DB failed!");
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
        PreparedStatement preparedStatement = dBConnection.prepareStatement("SELECT id, questionPhrase, grammarSection, exercise FROM question ORDER BY id ASC;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            Question question = new Question();
            question.setId(resultSet.getInt("id"));
            question.setQuestionPhrase(resultSet.getString("questionPhrase"));
            question.setGrammarSection(resultSet.getString("grammarSection"));
            question.setExercise(resultSet.getString("exercise"));
            questions.add(question);
        }
        return questions;
    }

    private ArrayList<Question> selectPossibleAndCorrectAnswersForEachQuestion(ArrayList<Question> questions) throws SQLException {
        for(int i=0;i<questions.size();i++) {
            PreparedStatement preparedStatementGetPossibleAnswers = dBConnection.prepareStatement("SELECT * FROM possibleansweres WHERE question_id = ? ORDER BY id ASC");
            preparedStatementGetPossibleAnswers.setString(1, Integer.toString(questions.get(i).getId()));
            ResultSet resultSetGetPossibleAnswers = preparedStatementGetPossibleAnswers.executeQuery();
            while(resultSetGetPossibleAnswers.next()) {
                questions.get(i).addPossibleAnswer(resultSetGetPossibleAnswers.getString("possibleAnswer"));
            }
            PreparedStatement preparedStatementGetCorrectAnswers = dBConnection.prepareStatement("SELECT * FROM correctansweres WHERE question_id = ? ORDER BY id ASC");
            preparedStatementGetCorrectAnswers.setString(1, Integer.toString(questions.get(i).getId()));
            ResultSet resultSetGetCorrectAnswers = preparedStatementGetCorrectAnswers.executeQuery();
            while(resultSetGetCorrectAnswers.next()) {
                questions.get(i).addCorrectAnswer(resultSetGetCorrectAnswers.getInt("correctAnswer"));
            }
        }
        return questions;
    }

    private void updateQuestion(Question question) throws SQLException {
        updateIdQuestionGrammarAndExercise(question);
        updatePossibleAndCorrectAnswers(question);
    }

    private void updateIdQuestionGrammarAndExercise(Question question) throws SQLException{
        PreparedStatement preparedStatementQuestion = dBConnection.prepareStatement("UPDATE question SET questionPhrase = ?, grammarSection = ?, exercise = ? WHERE id = ?;");
        preparedStatementQuestion.setString(1, question.getQuestionPhrase());
        preparedStatementQuestion.setString(2, question.getGrammarSection());
        preparedStatementQuestion.setString(3, question.getExercise());
        preparedStatementQuestion.setString(4,Integer.toString(question.getId()));
        preparedStatementQuestion.executeUpdate();
    }

    private void updatePossibleAndCorrectAnswers(Question question) throws SQLException {
        PreparedStatement preparedStatementDeleteOldPossibleAnswer = dBConnection.prepareStatement("DELETE FROM possibleansweres WHERE question_id = ?;");
        preparedStatementDeleteOldPossibleAnswer.setString(1, Integer.toString(question.getId()));
        preparedStatementDeleteOldPossibleAnswer.executeUpdate();

        PreparedStatement preparedStatementDeleteOldCorrectAnswer = dBConnection.prepareStatement("DELETE FROM correctansweres WHERE question_id = ?;");
        preparedStatementDeleteOldCorrectAnswer.setString(1, Integer.toString(question.getId()));
        preparedStatementDeleteOldCorrectAnswer.executeUpdate();

        for(String possibleAnswer: question.getPossibleAnswers()) {
            PreparedStatement preparedStatementPossibleAnswer = dBConnection.prepareStatement("INSERT INTO possibleAnsweres (question_id, possibleAnswer) VALUES (?, ?);");
            preparedStatementPossibleAnswer.setString(1, Integer.toString(question.getId()));
            preparedStatementPossibleAnswer.setString(2, possibleAnswer);
            preparedStatementPossibleAnswer.executeUpdate();
        }
        for(Integer correctAnswer: question.getCorrectAnswers()) {
            PreparedStatement preparedStatementCorrectAnswer = dBConnection.prepareStatement("INSERT INTO correctansweres (question_id, correctAnswer) VALUES (?, ?);");
            preparedStatementCorrectAnswer.setString(1, Integer.toString(question.getId()));
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
        PreparedStatement preparedStatementQuestion = dBConnection.prepareStatement("INSERT INTO question (id, questionPhrase, grammarSection, exercise) VALUE (?, ?, ?, ?);");
        preparedStatementQuestion.setString(1, Integer.toString(question.getId()));
        preparedStatementQuestion.setString(2, question.getQuestionPhrase());
        preparedStatementQuestion.setString(3, question.getGrammarSection());
        preparedStatementQuestion.setString(4,question.getExercise());
        preparedStatementQuestion.executeUpdate();
        for(String possibleAnswer: question.getPossibleAnswers()) {
            PreparedStatement preparedStatementPossibleAnswer = dBConnection.prepareStatement("INSERT INTO possibleAnsweres (question_id, possibleAnswer) VALUES (?, ?)");
            preparedStatementPossibleAnswer.setString(1, Integer.toString(question.getId()));
            preparedStatementPossibleAnswer.setString(2, possibleAnswer);
            preparedStatementPossibleAnswer.executeUpdate();
        }
        for(Integer correctAnswer: question.getCorrectAnswers()) {
            PreparedStatement preparedStatementCorrectAnswer = dBConnection.prepareStatement("INSERT INTO correctansweres (question_id, correctAnswer) VALUES (?, ?)");
            preparedStatementCorrectAnswer.setString(1, Integer.toString(question.getId()));
            preparedStatementCorrectAnswer.setString(2, Integer.toString(correctAnswer));
            preparedStatementCorrectAnswer.executeUpdate();
        }
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
        PreparedStatement selectAllQuestions = dBConnection.prepareStatement("SELECT id FROM question;");
        ResultSet idSet = selectAllQuestions.executeQuery();
        while(idSet.next()) {
            questionId = idSet.getInt("id");
        }
        return questionId;
    }

    private void updateAllQuestions(ArrayList<Question> questions) throws SQLException {
        PreparedStatement preparedStatementDeleteQuestions = dBConnection.prepareStatement("DELETE FROM question");
        preparedStatementDeleteQuestions.executeUpdate();

        PreparedStatement preparedStatementDeletePossibleAnswers = dBConnection.prepareStatement("DELETE FROM possibleansweres");
        preparedStatementDeletePossibleAnswers.executeUpdate();

        PreparedStatement preparedStatementCorrectAnswers = dBConnection.prepareStatement("DELETE FROM correctansweres");
        preparedStatementCorrectAnswers.executeUpdate();

        for(Question question:questions) {
            insertNewQuestion(question);
        }
    }
}
