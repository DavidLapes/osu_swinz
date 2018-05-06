package com.djenterprise.web.game;

import com.djenterprise.app.game.AnswerBO;
import com.djenterprise.app.game.GameStateBO;
import com.djenterprise.db.game.AnswerDAO;
import com.djenterprise.db.game.GameDAO;
import com.djenterprise.db.game.GameStateDAO;
import com.djenterprise.web.user.Keys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalTime;
import java.util.List;

@WebServlet(name = "GameCycleServlet", urlPatterns = {"/GameCycleServlet"})
public class GameCycleServlet extends HttpServlet {

    private static final int ROUND_LENGTH = 30; //seconds
    private static final int POINT_INCREASE = 10; //points

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try(PrintWriter out = response.getWriter()) {
            String gameID = request.getParameter("gameID");
            String alias = (String)request.getSession().getAttribute(Keys.ALIASKEY);
            LocalTime time = GameStateDAO.getStartingTime(gameID);
            int timeInt = time.toSecondOfDay();
            int answerID = Integer.parseInt(request.getParameter("answerID"));
            AnswerBO answer = AnswerDAO.getAnswer(answerID);
            try {

                if(GameStateDAO.isConnected(alias, gameID)) {
                    while (timeInt + ROUND_LENGTH >= LocalTime.now().toSecondOfDay() && !GameStateDAO.isBothConnected(gameID)) {
                        Thread.sleep(500);
                    }

                } else {

                    GameStateDAO.setConnected(true, gameID, alias);


                    while (timeInt + ROUND_LENGTH >= LocalTime.now().toSecondOfDay() && !GameStateDAO.isBothConnected(gameID)) {
                        Thread.sleep(500);
                    }

                    if (answer.isCorrect()) {
                        if (GameDAO.isPlayerOne(alias, gameID)) {
                            GameStateDAO.playerOnePointsIncrease(gameID, POINT_INCREASE);
                        } else {
                            GameStateDAO.playerTwoPointsIncrease(gameID, POINT_INCREASE);
                        }
                    }
                }
                GameStateBO gameState = GameStateDAO.getGameState(gameID);
                if (gameState.getNumberOfQuestions() == gameState.getCurrentRound()){
                    response.sendRedirect("");
                    return;
                }

                if(GameDAO.isPlayerOne(alias, gameID)){
                    GameStateDAO.nextRound(gameID);
                }

                Thread.sleep(1000);

                response.sendRedirect("gameCycle.jsp?gameID=" + gameID);

            } catch (InterruptedException ex){
                response.sendRedirect("index.jsp?err=UNKNOWN_REASON");
                return;
            }

            




        }
    }
}
