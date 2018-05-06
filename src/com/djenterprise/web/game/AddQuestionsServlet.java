package com.djenterprise.web.game;

import com.djenterprise.app.game.AnswerBO;
import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.game.AnswerDAO;
import com.djenterprise.db.game.QuestionDAO;
import com.djenterprise.web.user.Keys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddQuestionsServlet", urlPatterns = {"/AddQuestionsServlet"})
public class AddQuestionsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String textQuestion=request.getParameter("questionInput");
            String textAnswer1=request.getParameter("answerFirst");
            String textAnswer2=request.getParameter("answerSecond");
            String textAnswer3=request.getParameter("answerThird");
            String textAnswer4=request.getParameter("answerFourth");
            String correctAnswer = request.getParameter("correctAnswer");
            if(textQuestion == null || textQuestion.isEmpty() || textQuestion.equals("FILL QUESTION IN HERE")
                    || textAnswer1 == null ||textAnswer1.isEmpty() ||  textAnswer1.equals("FIRST ANSWER")
                    || textAnswer2 == null ||textAnswer2.isEmpty() ||  textAnswer2.equals("SECOND ANSWER")
                    || textAnswer3 == null ||textAnswer3.isEmpty() ||  textAnswer3.equals("THIRD ANSWER")
                    || textAnswer4 == null ||textAnswer4.isEmpty() ||  textAnswer4.equals("FOURTH ANSWER")
                    || correctAnswer == null || correctAnswer.isEmpty()) {
                response.sendRedirect("customQuestions.jsp?errMsg=EMPTY_FIELD");
                return;
            }

            QuestionBO question = new QuestionBO();
            question.setText(textQuestion);
            try {
                QuestionDAO.createQuestion(question);
            } catch (Exception ex){
                response.sendRedirect("customQuestions.jsp?errMsg=ALREADY_EXISTS");
                return;
            }
            question = QuestionDAO.getQuestion(question.getText());

            int truthfullness1 = 0;
            int truthfullness2 = 0;
            int truthfullness3 = 0;
            int truthfullness4 = 0;

            if (correctAnswer.equals("FIRST"))truthfullness1 = 1;
            if (correctAnswer.equals("SECOND"))truthfullness2 = 1;
            if (correctAnswer.equals("THIRD"))truthfullness3 = 1;
            if (correctAnswer.equals("FOURTH"))truthfullness4 = 1;

            AnswerBO answer1 = new AnswerBO();
            AnswerBO answer2 = new AnswerBO();
            AnswerBO answer3 = new AnswerBO();
            AnswerBO answer4 = new AnswerBO();
            answer1.setQuestionId(question.getQuestionId());
            answer2.setQuestionId(question.getQuestionId());
            answer3.setQuestionId(question.getQuestionId());
            answer4.setQuestionId(question.getQuestionId());
            answer1.setTruthfulness(truthfullness1);
            answer2.setTruthfulness(truthfullness2);
            answer3.setTruthfulness(truthfullness3);
            answer4.setTruthfulness(truthfullness4);
            answer1.setText(textAnswer1);
            answer2.setText(textAnswer2);
            answer3.setText(textAnswer3);
            answer4.setText(textAnswer4);
            AnswerDAO.createAnswer(answer1);
            AnswerDAO.createAnswer(answer2);
            AnswerDAO.createAnswer(answer3);
            AnswerDAO.createAnswer(answer4);

            QuestionDAO.fillAnswersToQuestion(question);
            if ((ArrayList) request.getSession().getAttribute(Keys.QUESTIONLISTKEY) == null) {
                request.getSession().setAttribute(Keys.QUESTIONLISTKEY, new ArrayList<QuestionBO>());
            }
            ArrayList<QuestionBO> list = (ArrayList<QuestionBO>)request.getSession().getAttribute(Keys.QUESTIONLISTKEY);
            list.add(question);
            request.setAttribute(Keys.QUESTIONLISTKEY, list);

            response.sendRedirect("customQuestions.jsp");

        }
    }
}
