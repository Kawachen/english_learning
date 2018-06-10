package Bean;

import Services.Answer.AnswerService;
import Services.Question.QuestionService;
import Services.User.UserService;
import Datamodel.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import utils.SessionUtils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@ManagedBean
@RequestScoped
public class ResultLogicBean {

    private User user;
    private Result result = new Result();
    private AnswerService answerService;

    public ResultLogicBean() {
        UserService userService = new UserService();
        this.user = userService.getUserByEmailAddress(SessionUtils.getSession().getAttribute("email").toString());
        answerService = new AnswerService(user);
        QuestionService questionService = new QuestionService();

        ArrayList<Answer> answers = this.answerService.getAnswers();

        //compare the questions and results
        if(questionService.getCountOfQuestions() == answers.size()) {
            for(int i = 0;i < questionService.getCountOfQuestions();i++) {
                for (int correctAnswer: questionService.getQuestionById(i+1).getCorrectAnswers()) {
                    boolean mistake = true;
                    for(int chosenAnswer: answers.get(i).getChosenAnswers()) {
                        if(correctAnswer == chosenAnswer) {
                            mistake = false;
                        }
                    }
                    if(mistake) {
                        Question question = questionService.getQuestionById(i+1);
                        MistakeAnswer mistakeAnswer = new MistakeAnswer(question.getId(), question.getQuestionPhrase(), answers.get(i).getChosenAnswers(), question.getCorrectAnswers(), question.getPossibleAnswers(), question.getGrammarSection(), question.getExercise());
                        this.result.addMistakeAnswer(mistakeAnswer);
                    }
                }
            }
        }
    }

    public Result getResult() {
        return this.result;
    }

    public long getTestWorkingTime() {
        return this.answerService.getTestWorkingTimeByUserId(this.user.getId());
    }

    public void downloadFile() {
        Document document = new Document();
        try {
            //Get response and outputStream
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ServletOutputStream servletOutputStream = response.getOutputStream();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, servletOutputStream);

            //write document
            document.open();
            document.add(new Paragraph("Deine Test-Ergebnisse"));
            document.add(new Paragraph("Deine Bearbeitungszeit in Millisekunden: "+this.getTestWorkingTime()));
            document.add(new Paragraph("Fehler insgesammt: "+this.result.getCountTotalMistakes()));
            document.add(new Paragraph("\n"));
            float[] columnWidth = {1,4,3,4,3,3,3};
            PdfPTable table = new PdfPTable(columnWidth);
            table.setWidthPercentage(100);
            table.addCell(getClassicTextCell("ID:"));
            table.addCell(getClassicTextCell("Frage:"));
            table.addCell(getClassicTextCell("Grammatik:"));
            table.addCell(getClassicTextCell("Antwortmöglichkeiten:"));
            table.addCell(getClassicTextCell("Richtige Antwort/en"));
            table.addCell(getClassicTextCell("Deine Antwort/en"));
            table.addCell(getClassicTextCell("Übungen:"));
            for(MistakeAnswer answer:this.result.getMistakeAnswers()) {
                table.addCell(getClassicTextCell(Integer.toString(answer.getQuestionId())));
                table.addCell(getClassicTextCell(answer.getQuestionPhrase()));
                table.addCell(getClassicTextCell(answer.getGrammarSection()));
                int i = 1;
                StringBuilder possibleAnswerString = new StringBuilder();
                for(String possibleAnswer:answer.getPossibleAnswers()) {
                    possibleAnswerString.append(i+". "+possibleAnswer+"\n");
                    i++;
                }
                table.addCell(getClassicTextCell(possibleAnswerString.toString()));
               StringBuilder correctAnswerString = new StringBuilder();
                for(int correctAnswer:answer.getCorrectAnswers()) {
                    correctAnswerString.append(Integer.toString(correctAnswer+1)+". "+answer.getPossibleAnswers().get(correctAnswer)+"\n");
                }
                table.addCell(getClassicTextCell(correctAnswerString.toString()));
                StringBuilder chosenAnswerString = new StringBuilder();
                for(int chosenAnswer: answer.getChosenAnswers()) {
                    chosenAnswerString.append(Integer.toString(chosenAnswer+1)+". "+answer.getPossibleAnswers().get(chosenAnswer)+"\n");
                }
                table.addCell(getClassicTextCell(chosenAnswerString.toString()));
                table.addCell(getClassicTextCell(answer.getExercise()));
            }
            document.add(table);
            document.close();

            //set response information
            response.setHeader("Content-Disposition", "attachment;filename=result.pdf");
            response.setContentType("application/pdf");

            //close outputStream and complete response
            servletOutputStream.flush();
            servletOutputStream.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch(Exception e) {
            System.err.println("Shit");
            e.printStackTrace();
        }
    }

    private PdfPCell getClassicTextCell(String text) {
        Font classicFont = FontFactory.getFont("Arial", 10);
        return new PdfPCell(new Paragraph(text, classicFont));
    }
}
