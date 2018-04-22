<%@ page import="com.djenterprise.web.user.Keys" %><%--
  Created by IntelliJ IDEA.
  User: davel
  Date: 19.03.2018
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
    <head>
        <title>DJahoot!</title>
        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="icon" href="${pageContext.request.contextPath}/images/djicon.png">
    </head>
    <body>
    <div class="formBox">
        <form action ="registration.jsp" method="post">
            <label class="gamePinLabel" for="code">DJAHOOT!</label>
            <%-- When you click / tab on / select this input field, default value disappears --%>
            <input class="gamePinInput" type="text" name="code" id="code" value="GAME PIN" onblur=" if (this.value === '') {this.value = 'GAME PIN';}" onfocus="if (this.value === 'GAME PIN') {this.value = '';}">
            <input class="gamePinSubmit" style="margin-top: -18px;" type="submit" value="ENTER" >
        </form>
        <%
            if( session.getAttribute(Keys.LOGINKEY) != null && ! ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                out.println("<form action=\"LogoutServlet\" method=\"post\">");
                out.println("<img src=\"images/man.png\" style=\"height: 60px; width: 60px; float: left;\">");
                out.println("<p class=\"loginText\">" + session.getAttribute(Keys.ALIASKEY).toString().toUpperCase() + "</p>");
                out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"LOG OUT\" style=\"margin-top: 10px;\">");
                out.println("</form>");
            } else {
                out.println("<form action=\"login.jsp\" method=\"post\">");
                out.println("<img src=\"images/man.png\" style=\"height: 60px; width: 60px; float: left;\">");
                out.println("<input class=\"gamePinSubmit\" type=\"submit\" value=\"LOG IN\" style=\"width:310px; margin-left: 30px;\">");
                out.println("</form>");
            }
        %>
    </div>
    </body>
</html>