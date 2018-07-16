package Logger;

import java.util.ArrayList;

public class QuestionLogicLogger {

    private ArrayList<Integer> activeQuestionLogics = new ArrayList<>();
    private boolean doesAnyQuestionGotPreviouslyChanged = false;
    private int id = 0;

    private static QuestionLogicLogger instance = null;

    public static QuestionLogicLogger getInstance() {
        if(instance == null) {
            synchronized (QuestionLogicLogger.class) {
                if(instance == null) {
                    instance = new QuestionLogicLogger();
                }
            }
        }
        return instance;
    }

    public void setPreviouslyChangedMarker() {
        doesAnyQuestionGotPreviouslyChanged = true;
    }

    public boolean doesAnyQuestionGotPreviouslyChanged() {
        return doesAnyQuestionGotPreviouslyChanged;
    }

    public int registerNewQuestionLogic() {
        activeQuestionLogics.add(id);
        int result = id;
        id++;
        return result;
    }

    public void logoutQuestionLogic(int id) {
        if(activeQuestionLogics.size() == 1 && activeQuestionLogics.get(0) == id) {
            activeQuestionLogics.remove(0);
            removePreviouslyChangedMarker();
        } else {
            for(int i = 0; i < activeQuestionLogics.size();i++) {
                if(activeQuestionLogics.get(i) == id) {
                    activeQuestionLogics.remove(i);
                }
            }
        }
    }

    private void removePreviouslyChangedMarker() {
        doesAnyQuestionGotPreviouslyChanged = false;
    }
}
