package com.djenterprise.web.game;

import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.game.GameDAO;
import com.djenterprise.db.game.GameStateDAO;
import com.djenterprise.web.user.Keys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "WaitingForOtherPlayerServlet", urlPatterns = {"/WaitingForOtherPlayerServlet"})
public class WaitingForOtherPlayerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gameId = request.getParameter("gameID");

        String alias = (String) request.getSession().getAttribute(Keys.ALIASKEY);

        if (gameId == null || gameId.isEmpty()){
            response.sendRedirect("index.jsp?errWrongGameID=2");
            return;
        }

        try{
            GameDAO.getGame(gameId);
        } catch(EntityInstanceNotFoundException ex){
            response.sendRedirect("index.jsp?errWrongGameID=1");
            return;
        }

        GameStateDAO.playerPresent(gameId, alias);

        while(!GameStateDAO.bothConnected(gameId)){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException ex){
                response.sendRedirect("index.jsp?userErrMsg=AUTHENTICATION_VIOLATED");
                return;
            }
        }
        //TODO Redirect
        response.sendRedirect("");

    }
}
