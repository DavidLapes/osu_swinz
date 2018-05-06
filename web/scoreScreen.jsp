<%@ page import="com.djenterprise.db.user.UserDAO" %>
<%@ page import="com.djenterprise.db.game.GameDAO" %>
<%@ page import="com.djenterprise.web.user.Keys" %>
<%@ page import="com.djenterprise.app.game.GameBO" %>
<%@ page import="com.djenterprise.app.user.UserBO" %>
<%@ page import="com.djenterprise.app.game.GameStateBO" %>
<%@ page import="com.djenterprise.db.game.GameStateDAO" %>
<%@ page import="com.djenterprise.db.exceptions.EntityInstanceNotFoundException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>DJahoot!</title>
        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="icon" href="${pageContext.request.contextPath}/images/djicon.png">
    </head>
    <body>
        <div style="width: 1900px; height: 256px; margin-top: 100px;">
            <img src="DisplayAvatarServlet?player=player_one&gameID=<%= request.getParameter("gameID")%>" name="playerOneImg" id="playerOneImg" style="height: 256px; width: 256px; float: left; margin-left: 360px;">
            <img src="DisplayAvatarServlet?player=player_two&gameID=<%= request.getParameter("gameID")%>" name="playerTwoImg" id="playerTwoImg" style="height: 256px; width: 256px; float: left; margin-left: 668px; margin-right: 360px;">
        </div>
        <%
            if( session.getAttribute(Keys.LOGINKEY) == null || ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                response.sendRedirect("index.jsp");
            } else {
                try {
                    GameBO game = GameDAO.getGame(request.getParameter("gameID"));
                    String gameID = request.getParameter("gameID");
                    UserBO loggedUser = UserDAO.getUser((String)session.getAttribute(Keys.LOGINKEY));
                    if( ! ( loggedUser.getAlias().equals(game.getPlayerOne()) || loggedUser.getAlias().equals(game.getPlayerTwo()) ) ) {
                        response.sendRedirect("index.jsp?userErrMsg=AUTHENTICATION_VIOLATED");
                    } else {
                        GameStateBO gamestate = GameStateDAO.getGameState(game.getGameId());

                        out.print("<div style=\"width: 1900px; height: 40px;\">");
                        out.print("<span style=\"float: left; color: white; margin-left: 360px; font-size: 40px; font-family: fantasy;\">"
                                + UserDAO.getUserByAlias(GameDAO.getGame(request.getParameter("gameID")).getPlayerOne()).getAlias()
                                + "</span>"
                        );

                        out.print("<span style=\"float: right; color: white; margin-right: 360px; font-size: 40px; font-family: fantasy;\">"
                                + UserDAO.getUserByAlias(GameDAO.getGame(request.getParameter("gameID")).getPlayerTwo()).getAlias()
                                + "</span>"
                        );
                        out.print("</div>");

                        out.print("<div style=\"width: 1900px; height: 40px; margin-top: 10px;\">");
                        out.print("<span style=\"float: left; color: white; margin-left: 360px; font-size: 40px; font-family: fantasy;\">"
                                + GameStateDAO.getGameState(gameID).getPlayerOnePoints()
                                + "</span>"
                        );

                        out.print("<span style=\"float: right; color: white; margin-right: 360px; font-size: 40px; font-family: fantasy;\">"
                                + GameStateDAO.getGameState(gameID).getPlayerTwoPoints()
                                + "</span>"
                        );
                        out.print("</div>");
                    }
                } catch (EntityInstanceNotFoundException ex) {
                    response.sendRedirect("index.jsp?err=WRONG_GAME_ID");
                }
            }
        %>
        <div class="regBox" style="margin-top: 10%; height: 250px; width: 1200px;">
            <%
                if( session.getAttribute(Keys.LOGINKEY) == null || ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                    response.sendRedirect("index.jsp");
                } else {
                    try {
                        GameBO game = GameDAO.getGame(request.getParameter("gameID"));
                        String gameID = request.getParameter("gameID");
                        UserBO loggedUser = UserDAO.getUser((String)session.getAttribute(Keys.LOGINKEY));
                        if( ! ( loggedUser.getAlias().equals(game.getPlayerOne()) || loggedUser.getAlias().equals(game.getPlayerTwo()) ) ) {
                            response.sendRedirect("index.jsp?userErrMsg=AUTHENTICATION_VIOLATED");
                        } else {
                            GameStateBO gamestate = GameStateDAO.getGameState(game.getGameId());
                        }
                    } catch (EntityInstanceNotFoundException ex) {
                        response.sendRedirect("index.jsp?err=WRONG_GAME_ID");
                    }
                }
            %>
        </div>
    </body>
</html>
