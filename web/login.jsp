<%--
  Created by IntelliJ IDEA.
  User: davel
  Date: 22.04.2018
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>DJahoot!</title>
        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="icon" href="${pageContext.request.contextPath}/images/djicon.png">
    </head>
    <body>
    <div class="formBox" style="margin-top: 20%;">
        <form action ="LoginServlet" method="post">
            <input class="loginInput" type ="text" name="username" id="username" value="USERNAME" onblur=" if (this.value === '') {this.value = 'USERNAME';}" onfocus="if (this.value === 'USERNAME') {this.value = '';}">
            <input class="loginInput" type="password" name="password" id="password" value="PASSWORD" onblur=" if (this.value === '') {this.value = 'PASSWORD';}" onfocus="if (this.value === 'PASSWORD') {this.value = '';}">
            <input class="gamePinSubmit" type="submit" value="LOG IN" >
        </form>
    </div>
    </body>
</html>
