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
        <link rel="icon" href="/images/djicon.png">
    </head>
    <body>
        <div class="regBox">
            <form action ="" method="post">
                <label for="username">Username</label>
                <input type = text name="username" id="username">
                <label for="password">Password</label>
                <input type="password" name="password" id="password">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" name="confirmPassword" id="confirmPassword">
                <label for="displayName">Display Name</label>
                <input type="text" name="displayName" id="displayName">
                <input type="submit" value="Register" name="submit" id="submit">
            </form>
        </div>
    </body>
</html>
