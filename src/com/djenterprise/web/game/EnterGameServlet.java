package com.djenterprise.web.game;

import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.game.GameDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EnterGameServlet", urlPatterns = {"/EnterGameServlet"})
public class EnterGameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter()) {
            String gameID = request.getParameter("code");

            if( gameID == null ) {
                response.sendRedirect("index.jsp?errGameID=NOT_EXISTING_GAME_ID");
            } else if( gameID.isEmpty() ) {
                response.sendRedirect("index.jsp?errGameID=NOT_EXISTING_GAME_ID");
            } else {
                try {
                    GameDAO.getGame(gameID);
                    response.sendRedirect("gameLobby.jsp?gameID=" + gameID);
                } catch (EntityInstanceNotFoundException ex) {
                    response.sendRedirect("index.jsp?errGameID=NOT_EXISTING_GAME_ID");
                }
            }
        }
    }
}
