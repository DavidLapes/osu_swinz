package com.djenterprise.web.game;

import com.djenterprise.app.game.GameConstruct;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.user.UserDAO;
import com.djenterprise.web.user.Keys;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CustomGameServlet", urlPatterns = {"/CustomGameServlet"})
public class CustomGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    //TODO Custom questions
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()) {

            if( request.getParameter("soflow").equals("HOW MANY QUESTIONS?") ) {
                response.sendRedirect("createGeneratedGame.jsp?errMsg=questionCount");
                return;
            }

            try {
                UserDAO.getUserByAlias(request.getParameter("playerOne"));
            } catch (EntityInstanceNotFoundException ex) {
                response.sendRedirect("createGeneratedGame.jsp?errMsg=playerOne");
                return;
            }

            try {
                UserDAO.getUserByAlias(request.getParameter("playerTwo"));
            } catch (EntityInstanceNotFoundException ex) {
                response.sendRedirect("createGeneratedGame.jsp?errMsg=playerTwo");
                return;
            }

            HttpSession session = request.getSession();

            String selectOption = request.getParameter("soflow");
            selectOption = selectOption.trim();

            int questionCount = Integer.parseInt(selectOption);

            String creator = (String) session.getAttribute(Keys.LOGINKEY);
            String playerOne = request.getParameter("playerOne");
            String playerTwo = request.getParameter("playerTwo");

            GameConstruct.constructGame( creator, playerOne, playerTwo, questionCount );

            RequestDispatcher view = request.getServletContext().getRequestDispatcher("/gameLobby.jsp");
            view.forward(request, response);
        }
    }
}
