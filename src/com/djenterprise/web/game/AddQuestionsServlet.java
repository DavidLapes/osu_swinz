package com.djenterprise.web.game;

import com.djenterprise.app.game.AnswerBO;
import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.game.QuestionDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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


            QuestionBO question = new QuestionBO();
            QuestionDAO.createQuestion(question);
            question = QuestionDAO.getQuestion(question.getText());

            boolean truthfullness1 = false;
            boolean truthfullness2 = false;
            boolean truthfullness3 = false;
            boolean truthfullness4 = false;
            /*switch (){
                case 1: truthfullness1 = true;
                        break;
                case 2: truthfullness2 = true;
                        break;
                case 3: truthfullness3 = true;
                        break;
                case 4: truthfullness4 = true;
                        break;
            }*/
            AnswerBO answer1 = new AnswerBO();
            answer1.setQuestionId(question.getQuestionId());
            AnswerBO answer2 = new AnswerBO();
            answer2.setQuestionId(question.getQuestionId());
            AnswerBO answer3 = new AnswerBO();
            answer3.setQuestionId(question.getQuestionId());
            AnswerBO answer4 = new AnswerBO();
            answer4.setQuestionId(question.getQuestionId());

        }
    }
}
