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

            //TODO Everything
        %>
        <div class="formBox">
            <form action ="GenerateGameServlet" method="post">
                <input class="regInput" type ="text" name="playerOne" id="playerOne" value="ALIAS OF PLAYER ONE" onblur=" if (this.value === '') {this.value = 'ALIAS OF PLAYER ONE';}" onfocus="if (this.value === 'ALIAS OF PLAYER ONE') {this.value = '';}">
                <input class="regInput" type="text" name="playerTwo" id="playerTwo" value="ALIAS OF PLAYER TWO" onblur=" if (this.value === '') {this.value = 'ALIAS OF PLAYER TWO';}" onfocus="if (this.value === 'ALIAS OF PLAYER TWO') {this.value = '';}">
                <input class="gamePinSubmit" type="submit" value="CREATE" name="submit" id="submit">
            </form>
        </div>
    </body>
</html>
