import Services.Question.QuestionService;
import Utilities.Question;

import java.io.BufferedReader;
import java.io.FileReader;

public class script {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Fragen.txt"));
            QuestionService questionService = new QuestionService();
            while(br.ready()) {
                System.out.println(br.readLine());
                Question question = new Question();

                String questionPhrase = br.readLine();
                String[] questionArray = questionPhrase.split("[(]");
                if(questionArray.length > 1) {
                    String questionFirst = questionArray[0];
                    String[] questionLast = questionArray[1].split("[)]");
                    question.setQuestionPhrase(questionFirst+questionLast[1]);
                    question.setGrammarSection(questionLast[0]);
                } else {
                    question.setQuestionPhrase(questionArray[0]);
                    question.setGrammarSection("");
                }

                //question.setQuestionPhrase(br.readLine());
                String[] answers = br.readLine().split("/");
                for (String answer: answers) {
                    question.addPossibleAnswer(answer);
                }
                String[] correctAnswers = br.readLine().split("/");
                for (String correctAnswer: correctAnswers) {
                    if(correctAnswer.equalsIgnoreCase("A")) {
                        question.addCorrectAnswer(0);
                    } else if(correctAnswer.equalsIgnoreCase("B")) {
                        question.addCorrectAnswer(1);
                    } else if(correctAnswer.equalsIgnoreCase("C")) {
                        question.addCorrectAnswer(2);
                    } else if(correctAnswer.equalsIgnoreCase("D")){
                        question.addCorrectAnswer(3);
                    } else {

                    }
                }
                question.setExercise(br.readLine());
                questionService.setNewQuestionInToDB(question);
            }
            br.close();
        } catch(Exception e) {
            System.err.println("file not found");
        }
    }
}
