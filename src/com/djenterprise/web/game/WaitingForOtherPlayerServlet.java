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

        //Redirect to main game if the game has started
        if (GameStateDAO.isGameStarted(gameId)){
            response.sendRedirect("gameCycle.jsp?gameID=" + gameId);
            return;
        }

        String alias = (String) request.getSession().getAttribute(Keys.ALIASKEY);

        if (gameId == null || gameId.isEmpty()){
            response.sendRedirect("index.jsp?err=WRONG_GAME_ID");
            return;
        }

        try{
            GameDAO.getGame(gameId);
        } catch(EntityInstanceNotFoundException ex){
            response.sendRedirect("index.jsp?err=WRONG_GAME_ID");
            return;
        }

        GameStateDAO.setConnected(true, gameId, alias);
        //Cycle to check if both players are connected once every second
        while(!GameStateDAO.isBothConnected(gameId)){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException ex){
                response.sendRedirect("index.jsp?userErrMsg=AUTHENTICATION_VIOLATED");
                return;
            }
        }
        //Sleep to synchronize
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex){
            response.sendRedirect("index.jsp?err=UNKNOWN_REASON");
        }
        //Set variable to false for answering purposes
        GameStateDAO.setConnected(false, gameId, alias);
        //Start game
        GameStateDAO.gameStart(gameId);
        //Put timer start into DB
        GameStateDAO.nowToDB(gameId);


        response.sendRedirect("gameCycle.jsp?gameID=" + gameId);

    }
}
