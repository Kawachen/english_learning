package Services.Answer;

import Datamodel.Answer;
import Datamodel.User;

import java.util.ArrayList;

public class AnswerService {

    private AnswerDBInterface answerDBService = new AnswerDBService();
    private User user;
    private ArrayList<Answer> answers;
    private int firstNewAnswerId = 1;

    public AnswerService(User user) {
        answers = this.answerDBService.selectAnswersByUserEmailAddressFromDB(user.getEmailAddress());
        this.user = user;
    }

    public void addNewAnswer(Answer answer) {
        boolean doesAnswerAlreadyExistInAnswerList = false;
        for(int i = 0; i < this.answers.size(); i++) {
            if(this.answers.get(i).getQuestionId() == answer.getQuestionId()) {
                this.answers.set(i, answer);
                doesAnswerAlreadyExistInAnswerList = true;
            }
        }
        if(!doesAnswerAlreadyExistInAnswerList) {
            this.answers.add(answer);
        }
    }

    public int getFirstNewAnswerQuestionId() {
        if(answers != null && answers.size() != 0) {
            firstNewAnswerId = answers.get(answers.size()-1).getQuestionId() + 1;
        }
        return firstNewAnswerId;
    }

    public void saveNewAnswers() {
        for(Answer answer:answers) {
            if(answer.getQuestionId() >= firstNewAnswerId) {
                this.answerDBService.insertNewAnswerForUserIdInToDB(answer, user);
            }
        }
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public Answer getAnswerByQuestionId(int questionId) {
        for(Answer answer:answers) {
            if(answer.getQuestionId() == questionId) {
                return answer;
            }
        }
        return null;
    }

    public void updateTestWorkingTimeByUserId(long newDuration, int userId) {
        this.answerDBService.updateTestWorkingTimeByUserIdInToDB(newDuration, userId);
    }

    public long getTestWorkingTimeByUserId(int userId) {
        return this.answerDBService.selectTestWorkingTimeByUserIdFromDB(userId);
    }
}
