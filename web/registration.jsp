<%--
  Created by IntelliJ IDEA.
  User: MacBook
  Date: 23.03.18
  Time: 12:10
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
        <div class="regBox">
            <form action ="RegistrationServlet" method="post">
                <input class="gamePinInput" type ="text" name="username" id="username" value="USERNAME" onblur=" if (this.value === '') {this.value = 'USERNAME';}" onfocus="if (this.value === 'USERNAME') {this.value = '';}">
                <input class="gamePinInput" type="password" name="password" id="password" value="PASSWORD" onblur=" if (this.value === '') {this.value = 'PASSWORD';}" onfocus="if (this.value === 'PASSWORD') {this.value = '';}">
                <input class="gamePinInput" type="password" name="confirmPassword" id="confirmPassword" value="PASSWORD" onblur=" if (this.value === '') {this.value = 'PASSWORD';}" onfocus="if (this.value === 'PASSWORD') {this.value = '';}">
                <input class="gamePinInput" type="text" name="alias" id="alias" value="ALIAS" onblur=" if (this.value === '') {this.value = 'ALIAS';}" onfocus="if (this.value === 'ALIAS') {this.value = '';}">
                <input type="file" accept="image" name="file" id="file">
                <label class="custom-file-upload" for="file">AVATAR</label>
                <input class="gamePinSubmit" type="submit" value="REGISTER" name="submit" id="submit">
            </form>
        </div>
    </body>
</html>
