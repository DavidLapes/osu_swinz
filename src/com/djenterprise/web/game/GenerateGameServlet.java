package com.djenterprise.web.game;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.game.GameConstruct;
import com.djenterprise.app.game.QuestionBO;
import com.djenterprise.db.game.GameDAO;
import com.djenterprise.db.game.QuestionDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GenerateGameServlet", urlPatterns = {"/GenerateGameServlet"})
public class GenerateGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    //TODO Generate game
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()) {

            GameConstruct.constructGame("David", "David", "Chymja");

            RequestDispatcher view = request.getServletContext().getRequestDispatcher("/index.jsp");
            view.forward(request, response);
        }
    }
}
