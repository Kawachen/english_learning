package Bean;

import Services.Answer.AnswerService;
import Services.Question.QuestionService;
import Services.User.UserService;
import Datamodel.Answer;
import Datamodel.Question;
import Datamodel.User;
import utils.SessionUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.sql.Timestamp;

@ManagedBean
@ViewScoped
public class QuestionLogicBean {
    private User user;

    private AnswerService answerService;
    private QuestionService questionService;

    private Question actualQuestion;
    private int actualQuestionId;
    private int firstQuestionId;

    private int[] actualChosenAnswers;

    private Timestamp startTime;

    public QuestionLogicBean(){
        UserService userService = new UserService();
        this.user = userService.getUserByEmailAddress(SessionUtils.getSession().getAttribute("email").toString());
        this.answerService = new AnswerService(user);
        this.actualQuestionId = this.answerService.getLatestAnswerQuestionId();
        this.firstQuestionId = this.actualQuestionId;
        this.startTime = new Timestamp(System.currentTimeMillis());
        this.questionService = new QuestionService();
    }

    public Question getActualQuestion() {
        if(this.actualQuestionId <= this.questionService.getCountOfQuestions() && this.actualQuestionId >= this.firstQuestionId) {
            this.actualQuestion = questionService.getQuestionById(actualQuestionId);
            this.setChosenAnswersIfThereIsAPreviousGivenAnswer();
            return this.actualQuestion;
        } else if(this.actualQuestionId < this.firstQuestionId) {
            this.actualQuestionId++;
            this.actualQuestion = questionService.getQuestionById(actualQuestionId);
            this.setChosenAnswersIfThereIsAPreviousGivenAnswer();
            return this.actualQuestion;
        } else {
            this.actualQuestion = null;
            return null;
        }
    }

    public void nextAndSaveAnswer() {
        if(actualQuestion != null) {
            this.createAnswerAndAddItToAnswerList();
            this.actualQuestionId++;
        }
    }

    public void previousAndSaveAnswer() {
        if(actualQuestion != null) {
            this.createAnswerAndAddItToAnswerList();
            this.actualQuestionId--;
        }
    }

    public String saveAnswerAndQuit() {
        if(actualQuestion != null) {
            this.createAnswerAndAddItToAnswerList();
        }
        this.answerService.saveNewAnswers();
        this.updateTestWorkingTime();
        return "quit";
    }

    private void createAnswerAndAddItToAnswerList() {
        Answer answer = new Answer(actualQuestion.getId());
        for(int chosenAnswer : this.actualChosenAnswers) {
            answer.addChosenAnswer(chosenAnswer);
        }
        this.answerService.addNewAnswer(answer);
        this.actualChosenAnswers = null;
    }

    private void setChosenAnswersIfThereIsAPreviousGivenAnswer() {
        Answer answer = this.answerService.getAnswerByQuestionId(actualQuestionId);
        if(answer != null) {
            this.actualChosenAnswers = new int[answer.getChosenAnswers().size()];
            for(int i = 0;i < answer.getChosenAnswers().size();i++) {
                this.actualChosenAnswers[i] = answer.getChosenAnswers().get(i);
            }
        }
    }

    private void updateTestWorkingTime() {
        Timestamp stopTime = new Timestamp(System.currentTimeMillis());
        long duration = stopTime.getTime() - this.startTime.getTime();
        this.answerService.updateTestWorkingTimeByUserId(duration, this.user.getId());
    }

    public int[] getActualChosenAnswers() {
        return actualChosenAnswers;
    }

    public void setActualChosenAnswers(int[] actualChosenAnswers) {
        this.actualChosenAnswers = actualChosenAnswers;
    }
}
