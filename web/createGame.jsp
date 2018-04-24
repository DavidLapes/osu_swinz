<%@ page import="com.djenterprise.web.user.Keys" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Title</title>
    </head>
    <body>
        <%
            if( session.getAttribute(Keys.LOGINKEY) == null || ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                response.sendRedirect("index.jsp");
            }
        %>
        <div class="formBox">
            <form action ="registration.jsp" method="post">
                <label class="gamePinLabel" for="code">DJAHOOT!</label>
                <%-- When you click / tab on / select this input field, default value disappears --%>
                <input class="gamePinInput" style="margin-top: -10px;" type="text" name="code" id="code" value="INVALID" onblur=" if (this.value === '') {this.value = 'GAME PIN';}" onfocus="if (this.value === 'GAME PIN') {this.value = '';}">
                <input class="gamePinSubmit" style="margin-top: -18px;" type="submit" value="INVALID" >
            </form>
            <form action="" method="get">
                <input class="gamePinSubmit" style="margin-top: -10px;" type="submit" value="INVALID">
            </form>
        </div>
    </body>
</html>
