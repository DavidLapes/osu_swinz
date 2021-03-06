package com.djenterprise.web.game;

import com.djenterprise.app.game.GameConstruct;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.user.UserDAO;
import com.djenterprise.web.user.Keys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GenerateGameServlet", urlPatterns = {"/GenerateGameServlet"})
public class GenerateGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter out = response.getWriter()) {

            try {
                UserDAO.getUserByAlias(request.getParameter("playerOne"));
            } catch (EntityInstanceNotFoundException ex) {
                response.sendRedirect("createGeneratedGame.jsp?errAlias=ALIAS_ONE_NOT_EXISTS");
                return;
            }

            try {
                UserDAO.getUserByAlias(request.getParameter("playerTwo"));
            } catch (EntityInstanceNotFoundException ex) {
                response.sendRedirect("createGeneratedGame.jsp?errAlias=ALIAS_TWO_NOT_EXISTS");
                return;
            }

            if( UserDAO.getUserByAlias(request.getParameter("playerOne")).getAlias()
                    .equals(
                    UserDAO.getUserByAlias(request.getParameter("playerTwo")).getAlias())
                    ) {
                response.sendRedirect("createGeneratedGame.jsp?errAlias=ALIASES_SAME");
                return;
            }

            HttpSession session = request.getSession();

            String creator = (String) session.getAttribute(Keys.LOGINKEY);
            String playerOne = request.getParameter("playerOne");
            String playerTwo = request.getParameter("playerTwo");

            String gameID;

            if( request.getParameter("questionCount") != null ) {
                gameID = GameConstruct.constructGame(creator, playerOne, playerTwo, Integer.parseInt(request.getParameter("questionCount")));
            } else {
                gameID = GameConstruct.constructGame(creator, playerOne, playerTwo);
            }

            response.sendRedirect("/gameLobby.jsp?gameID=" + gameID);
        }
    }
}
