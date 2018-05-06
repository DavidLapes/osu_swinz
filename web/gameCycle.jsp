<%@ page import="com.djenterprise.web.user.Keys" %>
<%@ page import="com.djenterprise.db.exceptions.EntityInstanceNotFoundException" %>
<%@ page import="com.djenterprise.db.user.UserDAO" %>
<%@ page import="com.djenterprise.app.user.UserBO" %>
<%@ page import="com.djenterprise.db.game.GameDAO" %>
<%@ page import="com.djenterprise.app.game.GameBO" %>
<%@ page import="com.djenterprise.app.game.QuestionBO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.djenterprise.db.game.GameStateDAO" %>
<%@ page import="com.djenterprise.db.game.QuestionDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>DJahoot!</title>
        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="icon" href="${pageContext.request.contextPath}/images/djicon.png">
    </head>
    <body>

        <div style="width: 1900px; height: 256px; margin-top: 100px;">
            <img src="DisplayAvatarServlet?player=player_one&gameID=<%= request.getParameter("gameID")%>" name="playerOneImg" id="playerOneImg" style="height: 256px; width: 256px; float: left; margin-left: 300px;">
            <img src="DisplayAvatarServlet?player=player_two&gameID=<%= request.getParameter("gameID")%>" name="playerTwoImg" id="playerTwoImg" style="height: 256px; width: 256px; float: left; margin-left: 788px; margin-right: 300px;">
        </div>
        <div style="width: 1900px; height: 40px;">
            <span style="float:left; color: white; margin-left: 300px; font-size: 40px; font-family: fantasy"><%=UserDAO.getUserByAlias(GameDAO.getGame(request.getParameter("gameID")).getPlayerOne()).getAlias()%></span>
            <span style="float: right; color: white; margin-right: 300px; font-size: 40px; font-family: fantasy"><%=UserDAO.getUserByAlias(GameDAO.getGame(request.getParameter("gameID")).getPlayerTwo()).getAlias()%></span>
        </div>
        <div class="regBox" style="margin-top: 10%; height: 900px;">
            <%
                if( session.getAttribute(Keys.LOGINKEY) == null || ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                    response.sendRedirect("index.jsp");
                } else {
                    try {
                        GameBO game = GameDAO.getGame(request.getParameter("gameID"));
                        UserBO loggedUser = UserDAO.getUser((String)session.getAttribute(Keys.LOGINKEY));
                        if( ! ( loggedUser.getAlias().equals(game.getPlayerOne()) || loggedUser.getAlias().equals(game.getPlayerTwo()) ) ) {
                            response.sendRedirect("index.jsp?userErrMsg=AUTHENTICATION_VIOLATED");
                        } else {
                            String gameID = request.getParameter("gameID");
                            List<QuestionBO> list = GameDAO.getQuestions(GameDAO.getGame(gameID));
                            QuestionBO question = list.get(GameStateDAO.getCurrentRound(gameID));
                            QuestionDAO.fillAnswersToQuestion(question);

                            out.println("<form action=\"GameCycleServlet?answerID=1?gameID=" + request.getParameter("gameID") + "\" method=\"post\">");
                            out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"" + question.getAnswers().get(0) +"\" style=\"width:510px; margin-left: 30px; margin-top: 4px; text-indent: -90px;\">");
                            out.println("</form>");

                            out.println("<form action=\"GameCycleServlet?answerID=2?gameID=" + request.getParameter("gameID") + "\" method=\"post\">");
                            out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"" + question.getAnswers().get(1) +"\" style=\"width:510px; margin-left: 30px; margin-top: 4px; text-indent: -90px;\">");
                            out.println("</form>");

                            out.println("<form action=\"GameCycleServlet?answerID=3?gameID=" + request.getParameter("gameID") + "\" method=\"post\">");
                            out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"" + question.getAnswers().get(2) +"\" style=\"width:510px; margin-left: 30px; margin-top: 4px; text-indent: -90px;\">");
                            out.println("</form>");

                            out.println("<form action=\"GameCycleServlet?answerID=4?gameID=" + request.getParameter("gameID") + "\" method=\"post\">");
                            out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"" + question.getAnswers().get(3) +"\" style=\"width:510px; margin-left: 30px; margin-top: 4px; text-indent: -90px;\">");
                            out.println("</form>");
                        }
                    } catch (EntityInstanceNotFoundException ex) {
                        response.sendRedirect("index.jsp?err=WRONG_GAME_ID");
                    }
                }
            %>
            <form action="GameCycleServlet?gameID=<%= request.getParameter("gameID")%>" method="post">
                <input class="gamePinSubmit" type="button" value="THIS DOES NOTHING">
            </form>
        </div>
    </body>
</html>
