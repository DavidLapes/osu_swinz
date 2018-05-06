<%@ page import="com.djenterprise.web.user.Keys" %>
<%@ page import="com.djenterprise.db.game.GameDAO" %>
<%@ page import="com.djenterprise.app.game.GameBO" %>
<%@ page import="com.djenterprise.db.exceptions.EntityInstanceNotFoundException" %>
<%@ page import="com.djenterprise.app.user.UserBO" %>
<%@ page import="com.djenterprise.db.user.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>DJahoot!</title>
        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="icon" href="${pageContext.request.contextPath}/images/djicon.png">
    </head>
    <body>
        <%
            GameBO game = null;
            if( session.getAttribute(Keys.LOGINKEY) == null || ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                response.sendRedirect("index.jsp");
            } else {
                try {
                    game = GameDAO.getGame(request.getParameter("gameID"));
                    UserBO loggedUser = UserDAO.getUser((String)session.getAttribute(Keys.LOGINKEY));
                    if( ! ( loggedUser.getAlias().equals(game.getPlayerOne()) || loggedUser.getAlias().equals(game.getPlayerTwo()) ) ) {
                        response.sendRedirect("index.jsp?userErrMsg=AUTHENTICATION_VIOLATED");
                    }
                } catch (EntityInstanceNotFoundException ex) {
                    response.sendRedirect("index.jsp?err=WRONG_GAME_ID");
                }
            }
        %>

        <div style="width: 1900px; height: 256px; margin-top: 100px;">
            <img src="DisplayAvatarServlet?player=player_one&gameID=<%= request.getParameter("gameID")%>" name="playerOneImg" id="playerOneImg" style="height: 256px; width: 256px; float: left; margin-left: 300px;">
            <img src="DisplayAvatarServlet?player=player_two&gameID=<%= request.getParameter("gameID")%>" name="playerTwoImg" id="playerTwoImg" style="height: 256px; width: 256px; float: left; margin-left: 788px; margin-right: 300px;">
        </div>
        <div style="width: 1900px; height: 40px;">
            <span style="float:left; color: white; margin-left: 300px; font-size: 40px; font-family: fantasy"><%=UserDAO.getUserByAlias(GameDAO.getGame(request.getParameter("gameID")).getPlayerOne()).getAlias()%></span>
            <span style="float: right; color: white; margin-right: 300px; font-size: 40px; font-family: fantasy"><%=UserDAO.getUserByAlias(GameDAO.getGame(request.getParameter("gameID")).getPlayerTwo()).getAlias()%></span>
        </div>
        <div class="regBox" style="margin-top: 5%;">
            <form action="WaitingForOtherPlayerServlet?gameID=<%= request.getParameter("gameID")%>" method="post">
                <%
                    if( game != null ) {
                        out.print("<span style=\"text-align: center; font-size: 50px; margin-left: 200px; color: white;\">" + game.getGameId() + "</span>");
                    }
                %>
                <input class="gamePinSubmit" type="submit" value="I AM READY!" style="margin-top: 30px;">
            </form>
        </div>
    </body>
</html>
