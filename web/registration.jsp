<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>DJahoot!</title>
        <link rel="stylesheet" type="text/css" href="style.css">
        <link rel="icon" href="${pageContext.request.contextPath}/images/djicon.png">
        <script>
            function theFocus(obj) {
                var tooltip = document.getElementById("tooltip");
                tooltip.innerHTML = obj.title;
                tooltip.style.display = "block";
                tooltip.style.top = obj.offsetTop - tooltip.offsetHeight + "px";
                tooltip.style.left = obj.offsetLeft + "px";
            }

            function theBlur(obj) {
                var tooltip = document.getElementById("tooltip");
                tooltip.style.display = "none";
                tooltip.style.top = "-99px";
                tooltip.style.left = "-99px";
            }
        </script>
    </head>
    <body>
        <div class="regBox" style="margin-top: 5%">
            <c:if test="${param.errMsgDef == '0'}">
                <p class="errorTextRegistration">Please, fill all fields.</p>
            </c:if>

            <c:if test="${param.errMsgAlias == '1'}">
                <p class="errorTextRegistration">Please, check your alias.</p>
            </c:if>

            <c:if test="${param.errMsgPass == '2'}">
                <p class="errorTextRegistration">Please, check your password.</p>
            </c:if>

            <c:if test="${param.errMsgUser == '3'}">
                <p class="errorTextRegistration">Please, check your username..</p>
            </c:if>

            <c:if test="${param.errMsgConfirm == '4'}">
                <p class="errorTextRegistration">Passwords must match.</p>
            </c:if>

            <c:if test="${param.errMsgAliasTaken == '11'}">
                <p class="errorTextRegistration">Alias is already taken.</p>
            </c:if>

            <c:if test="${param.errMsgUserTaken == '33'}">
                <p class="errorTextRegistration">Username is already taken.</p>
            </c:if>
            <form action ="RegistrationServlet" method="post" enctype="multipart/form-data">
                <input class="regInput" type ="text" title="Please, enter 3 to 32 latin letters or numbers" name="username" id="username" value="USERNAME" onblur=" if (this.value === '') {this.value = 'USERNAME';} theBlur(this);" onfocus="if (this.value === 'USERNAME') {this.value = '';} theFocus(this);">
                <input class="regInput" type="password" title="Please, enter 3 to 32 characters. &#013; - At least one uppercase &#013; - At least one lowercase &#013; - At least one number &#013; - No whitespaces." name="password" id="password" value="PASSWORD" onblur=" if (this.value === '') {this.value = 'PASSWORD';} theBlur(this)" onfocus="if (this.value === 'PASSWORD') {this.value = '';} theFocus(this)">
                <input class="regInput" type="password" title="Please, confirm your password." name="confirmPassword" id="confirmPassword" value="PASSWORD" onblur=" if (this.value === '') {this.value = 'PASSWORD';} theBlur(this)" onfocus="if (this.value === 'PASSWORD') {this.value = '';} theFocus(this)">
                <input class="regInput" type="text" title="Please, enter 3 to 32 alphabetical, numerical characters or . _ -" name="alias" id="alias" value="ALIAS" onblur=" if (this.value === '') {this.value = 'ALIAS';} theBlur(this);" onfocus="if (this.value === 'ALIAS') {this.value = '';} theFocus(this)">
                <input type="file" accept="image" name="file" id="file">
                <label class="custom-file-upload" for="file">AVATAR</label>
                <input class="gamePinSubmit" type="submit" value="REGISTER" name="submit" id="submit">
                <div class="tooltip" id="tooltip"></div>
            </form>
        </div>
    </body>
</html>
