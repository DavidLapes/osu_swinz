package com.djenterprise.web.user;

import com.djenterprise.app.game.GameBO;
import com.djenterprise.app.user.UserBO;
import com.djenterprise.db.exceptions.EntityInstanceNotFoundException;
import com.djenterprise.db.game.GameDAO;
import com.djenterprise.db.user.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(name = "DisplayAvatarServlet", urlPatterns = {"/DisplayAvatarServlet"})
public class DisplayAvatarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        while( true ) {

            try {

                UserBO user;

                if (
                        request.getParameter("player") != null
                                && !request.getParameter("player").isEmpty()
                                &&
                                (
                                        request.getParameter("player").equals("player_one") ||
                                                request.getParameter("player").equals("player_two")
                                )
                        ) {

                    String gameID = request.getParameter("gameID");


                    if (
                            request.getParameter("gameID") == null
                                    || request.getParameter("gameID").isEmpty()

                            ) {
                        response.sendRedirect("index.jsp?gameIDError=NO_GAME_ID_ERROR");
                        return;
                    } else {
                        try {
                            GameDAO.getGame(gameID);
                        } catch (EntityInstanceNotFoundException ex) {
                            response.sendRedirect("index.jsp?gameIDError=NOT_EXISTING_GAME");
                            return;
                        }
                    }


                    String playerParameter = request.getParameter("player");
                    GameBO game = GameDAO.getGame(gameID);

                    if (playerParameter.equals("player_one")) {
                        user = UserDAO.getUserByAlias(game.getPlayerOne());
                    } else {
                        user = UserDAO.getUserByAlias(game.getPlayerTwo());
                    }

                } else {
                    user = UserDAO.getUser((String) request.getSession().getAttribute(Keys.LOGINKEY));
                }

                try {

                    if (user.getAvatar() != null) {
                        OutputStream output = response.getOutputStream();
                        response.setContentType("image/jpg");
                        output.write(
                                user.getAvatar().getBytes(1, (int) user.getAvatar().length())
                        );
                    } else {
                        user = UserDAO.getUser("root_username");
                        OutputStream output = response.getOutputStream();
                        response.setContentType("image/jpg");
                        output.write(
                                user.getAvatar().getBytes(1, (int) user.getAvatar().length())
                        );
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                return;
            } catch (RuntimeException ex) {
                //
            }
        }
    }
}
