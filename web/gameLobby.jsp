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
            if( session.getAttribute(Keys.LOGINKEY) == null || ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                response.sendRedirect("index.jsp");
            } else {
                try {
                    GameBO game = GameDAO.getGame(request.getParameter("gameID"));
                    UserBO loggedUser = UserDAO.getUser(Keys.LOGINKEY);
                    if( ! ( loggedUser.getAlias().equals(game.getPlayerOne()) || loggedUser.getAlias().equals(game.getPlayerTwo()) ) ) {
                        response.sendRedirect("index.jsp?userErrMsg=AUTHENTICATION_VIOLATED");
                    }
                } catch (EntityInstanceNotFoundException ex) {
                    response.sendRedirect("index.jsp");
                }
            }
        %>
        <div>
            <img src="DisplayAvatarServlet?player=player_one&gameID=<%= request.getParameter("gameID")%>" name="playerOneImg" id="playerOneImg" style="height: 256px; width: 256px; float: left; margin-left: 300px;">
            <img src="DisplayAvatarServlet?player=player_two&gameID=<%= request.getParameter("gameID")%>" name="playerTwoImg" id="playerTwoImg" style="height: 256px; width: 256px; float: left; margin-left: 820px;">
        </div>
        <div class="regBox" style="margin-top: 2%;">

        </div>
    </body>
</html>
