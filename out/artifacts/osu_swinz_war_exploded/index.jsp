<%@ page import="com.djenterprise.web.user.Keys" %>
<%@ page import="com.djenterprise.db.user.UserDAO" %><%--
  Created by IntelliJ IDEA.
  User: davel
  Date: 19.03.2018
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
    <head>
        <title>DJahoot!</title>
        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="icon" href="${pageContext.request.contextPath}/images/djicon.png">
        <script>
            function redirectToGameCreation() {
                var isUserLoggedIn = <%= session.getAttribute(Keys.LOGINKEY) != null %>;
                if( isUserLoggedIn ) {
                    window.location = "createGame.jsp";
                } else {
                    alert("You must first log in to DJAHOOT to be able to create or enter games. Please, log in or sign up.");
                }
            }

            function enterGame() {
                var isUserLoggedIn = <%= session.getAttribute(Keys.LOGINKEY) != null %>;
                if( isUserLoggedIn ) {
                    document.getElementById("enterGameForm").submit();
                } else {
                    alert("You must first log in to DJAHOOT to be able to create or enter games. Please, log in or sign up.");
                }
            }
        </script>
    </head>
    <body>

        <c:if test="${param.userErrMsg == 'AUTHENTICATION_VIOLATED'}">
            <script>
                alert("You don't belong to that game! Please, play with YOUR friends!");
            </script>
        </c:if>

        <div class="formBox" style="margin-top: 10%;">
            <form action="EnterGameServlet" method="get" id="enterGameForm">
                <label class="gamePinLabel" for="code">DJAHOOT!</label>
                <%-- When you click / tab on / select this input field, default value disappears --%>
                <input class="gamePinInput" style="margin-top: -10px;" type="text" name="code" id="code" value="GAME PIN" onblur=" if (this.value === '') {this.value = 'GAME PIN';}" onfocus="if (this.value === 'GAME PIN') {this.value = '';}">
                <input class="gamePinSubmit" style="margin-top: -16px;" type="button" onclick="enterGame()" value="ENTER" >
            </form>
            <form action="javascript:redirectToGameCreation()" method="post">
                <input class="gamePinSubmit" style="margin-top: -16px; margin-bottom: 0px;" type="submit" value="CREATE GAME">
            </form>
            <%
                if( session.getAttribute(Keys.LOGINKEY) != null && ! ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                    out.println("<img src=\"DisplayAvatarServlet\" name=\"userImg\" style=\"height: 60px; width: 60px; float: left; margin-top: 4px;\">");
                    out.println("<label class=\"loginText\" for=\"userImg\">" + session.getAttribute(Keys.ALIASKEY).toString().toUpperCase() + "</label>");
                    out.println("<form action=\"editUser.jsp\" method=\"get\">");
                    out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"EDIT ACCOUNT\" style=\"margin-top: 4px; margin-bottom: 0px;\">");
                    out.println("</form>");
                    out.println("<form action=\"LogoutServlet\" method=\"get\">");
                    out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"LOG OUT\" style=\"margin-top: 4px;\">");
                    out.println("</form>");
                } else {
                    out.println("<form action=\"login.jsp\" method=\"get\">");
                    out.println("<img src=\"images/man.png\" style=\"height: 60px; width: 60px; float: left; margin-top: 4px;\">");
                    out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"LOG IN\" style=\"width:510px; margin-left: 30px; margin-top: 4px; text-indent: -90px;\">");
                    out.println("</form>");
                    out.println("<form action=\"registration.jsp\" method=\"post\">");
                    out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"REGISTER\" style=\"margin-top: -16px;\">");
                    out.println("</form>");
                }
            %>
        </div>
    </body>
</html>