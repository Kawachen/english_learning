package Bean;

import Enums.EditQuestionError;
import Enums.EditQuestionResult;
import Services.Question.QuestionService;
import Datamodel.Question;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.ArrayList;

@ManagedBean
@RequestScoped
public class EditQuestionBean {


    private QuestionService questionService = new QuestionService();

    private int questionId = 0;

    private String questionPhrase;
    private String grammarSection;
    private String exercise;
    private int[] correctAnswers = new int[4];
    private String[] possibleAnswers = new String[4];

    //Error/Result Messages
    private EditQuestionResult editQuestionResult = null;
    private EditQuestionError editQuestionError = EditQuestionError.NO_ERROR_DETECTED;

    public EditQuestionBean() {
        for (int s = 0; s < possibleAnswers.length; s++) {
            possibleAnswers[s] = "";
        }
        for (int i = 0; i < correctAnswers.length; i++) {
            correctAnswers[i] = -1;
        }
    }

    public String editQuestion() {
        return "editQuestion";
    }

    public String editQuestion(int id) {
        this.questionId = id;
        return "editQuestion";
    }

    public String deleteQuestion() {
        this.questionService.deleteQuestionByIdAndUpdateDB(questionId);
        return "allQuestions";
    }

    public String deleteQuestion(int questionId) {
        this.questionId = questionId;
        this.questionService.deleteQuestionByIdAndUpdateDB(questionId);
        return "allQuestions";
    }

    public void saveQuestion() {
        ArrayList<Integer> correctAnswerList = convertCorrectAnswers();
        ArrayList<String> possibleAnswerList = convertPossibleAnswers();
        if(this.fitsPossibleAnswersToCorrectAnswersCheck(possibleAnswerList, correctAnswerList)) {
            Question question = this.questionService.getQuestionById(questionId);
            question.setQuestionPhrase(this.questionPhrase);
            question.setGrammarSection(this.grammarSection);
            question.setExercise(this.exercise);
            question.setPossibleAnswers(possibleAnswerList);
            question.setCorrectAnswers(correctAnswerList);
            this.questionService.setQuestionById(questionId, question);
            this.questionService.updateQuestionInToDB(question);
            this.editQuestionResult = EditQuestionResult.SUCCESSFULLY_EDIT_QUESTION;
        } else {
            this.editQuestionError = EditQuestionError.CORRECT_ANSWERS_NOT_VALID_ERROR;
        }
    }

    public void saveNewQuestion() {
        ArrayList<Integer> correctAnswerList = convertCorrectAnswers();
        ArrayList<String> possibleAnswerList = convertPossibleAnswers();
        if(this.fitsPossibleAnswersToCorrectAnswersCheck(possibleAnswerList, correctAnswerList)) {
            Question question = new Question();
            question.setQuestionPhrase(this.questionPhrase);
            question.setGrammarSection(this.grammarSection);
            question.setExercise(this.exercise);
            question.setPossibleAnswers(possibleAnswerList);
            question.setCorrectAnswers(correctAnswerList);
            this.questionService.addNewQuestion(question);
            this.questionService.setNewQuestionInToDB(question);
            this.editQuestionResult = EditQuestionResult.SUCCESSFULLY_ADDED_NEW_QUESTION;
        } else {
            this.editQuestionError = EditQuestionError.CORRECT_ANSWERS_NOT_VALID_ERROR;
        }
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public ArrayList<Question> getQuestions() {
        return questionService.getAllQuestions();
    }

    public void setQuestions(ArrayList<Question> questions) {
    }

    public Question getQuestion() {
        return this.questionService.getQuestionById(this.questionId);
    }

    public void setQuestion(Question question) {
        this.questionService.setQuestionById(this.questionId, question);
    }

    public EditQuestionResult getEditQuestionResult() {
        return editQuestionResult;
    }

    public EditQuestionError getEditQuestionError() {
        return editQuestionError;
    }

    public String getQuestionPhrase() {
        if(questionService.getQuestionById(questionId) != null) questionPhrase = questionService.getQuestionById(questionId).getQuestionPhrase();
        return questionPhrase;
    }

    public void setQuestionPhrase(String questionPhrase) {
        this.questionPhrase = questionPhrase;
    }

    public String getGrammarSection() {
        if(questionService.getQuestionById(questionId) != null) grammarSection = questionService.getQuestionById(questionId).getGrammarSection();
        return grammarSection;
    }

    public void setGrammarSection(String grammarSection) {
        this.grammarSection = grammarSection;
    }

    public String getExercise() {
        if(questionService.getQuestionById(questionId) != null) exercise = questionService.getQuestionById(questionId).getExercise();
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getPossibleAnswer1() {
        Question question = questionService.getQuestionById(questionId);
        int i = 0;
        if(question != null) {
            if(i < question.getPossibleAnswers().size())possibleAnswers[i] = question.getPossibleAnswers().get(i);
        }
        return possibleAnswers[i];
    }

    public String getPossibleAnswer2() {
        Question question = questionService.getQuestionById(questionId);
        int i = 1;
        if(question != null) {
            if(i < question.getPossibleAnswers().size())possibleAnswers[i] = question.getPossibleAnswers().get(i);
        }
        return possibleAnswers[i];
    }

    public String getPossibleAnswer3() {
        Question question = questionService.getQuestionById(questionId);
        int i = 2;
        if(question != null) {
            if(i < question.getPossibleAnswers().size())possibleAnswers[i] = question.getPossibleAnswers().get(i);
        }
        return possibleAnswers[i];
    }

    public String getPossibleAnswer4() {
        Question question = questionService.getQuestionById(questionId);
        int i = 3;
        if(question != null) {
            if(i < question.getPossibleAnswers().size())possibleAnswers[i] = question.getPossibleAnswers().get(i);
        }
        return possibleAnswers[i];
    }

    public void setPossibleAnswer1(String possibleAnswer1) {
        possibleAnswers[0] = possibleAnswer1;
    }

    public void setPossibleAnswer2(String possibleAnswer2) {
        possibleAnswers[1] = possibleAnswer2;
    }

    public void setPossibleAnswer3(String possibleAnswer3) {
        possibleAnswers[2] = possibleAnswer3;
    }

    public void setPossibleAnswer4(String possibleAnswer4) {
        possibleAnswers[3] = possibleAnswer4;
    }

    //this is just here to show what a shitty framework jsf is
    public String[] getPossibleAnswers() {
        Question question = questionService.getQuestionById(questionId);
        if(question != null)
        for (int i = 0; i < possibleAnswers.length; i++) {
            if(i < question.getPossibleAnswers().size()) possibleAnswers[i] = question.getPossibleAnswers().get(i);
        }
        return possibleAnswers;
    }

    public void setPossibleAnswers(String[] possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public int[] getCorrectAnswers() {
        Question question = questionService.getQuestionById(questionId);
        if(question != null) {
            for (int i = 0; i < correctAnswers.length; i++) {
                if(i < question.getCorrectAnswers().size()) correctAnswers[i] = question.getCorrectAnswers().get(i);
            }
        }
        return correctAnswers;
    }

    public void setCorrectAnswers(int[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    private boolean fitsPossibleAnswersToCorrectAnswersCheck(ArrayList<String> possibleAnswers, ArrayList<Integer> correctAnswers) {
        for(Integer correctAnswer: correctAnswers) {
            if(possibleAnswers.size() < correctAnswer+1) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<Integer> convertCorrectAnswers() {
        ArrayList<Integer> correctAnswerList = new ArrayList<>();

        for (int s = 0; s < correctAnswers.length; s++) {
            if(correctAnswers[s] != -1) {
                correctAnswerList.add(correctAnswers[s]);
            }
        }
        return correctAnswerList;
    }

    private ArrayList<String> convertPossibleAnswers() {
        ArrayList<String> possibleAnswerList = new ArrayList<>();
        for (int i = 0; i < possibleAnswers.length; i++) {
            if(!possibleAnswers[i].equalsIgnoreCase("")) {
                possibleAnswerList.add(possibleAnswers[i]);
            }
        }
        return possibleAnswerList;
    }
}
