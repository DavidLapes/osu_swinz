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
            <form action ="createGeneratedGame.jsp" method="post">
                <input class="createGameSubmit" type="submit" value="GENERATE GAME" >
            </form>
            <form action="createCustomGame.jsp" method="post">
                <input class="createGameSubmit" type="submit" value="CREATE CUSTOM GAME">
            </form>
        </div>
    </body>
</html>
