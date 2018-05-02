<%@ page import="com.djenterprise.web.user.Keys" %>
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
            }
        %>
        <div class="formBox">
            <form action ="GenerateGameServlet" method="post">
                <input class="createGameSubmit" type="submit" value="GENERATE GAME" >
            </form>
            <form action="createCustomGame.jsp" method="post">
                <input class="createGameSubmit" type="submit" value="CREATE CUSTOM GAME">
            </form>
            <%
                if( session.getAttribute(Keys.LOGINKEY) != null && ! ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                    out.println("<form action=\"LogoutServlet\" method=\"get\">");
                    out.println("<img src=\"images/man.png\" name=\"userImg\" style=\"height: 60px; width: 60px; float: left; margin-top: 4px;\">");
                    out.println("<label class=\"loginText\" for=\"userImg\">" + session.getAttribute(Keys.ALIASKEY).toString().toUpperCase() + "</label>");
                    out.println("<input class=\"createGameSubmit\" type=\"submit\" value=\"LOG OUT\" style=\"margin-top: 4px;\">");
                    out.println("</form>");
                }
            %>
        </div>
    </body>
</html>
