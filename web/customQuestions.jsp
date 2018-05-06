<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.djenterprise.web.user.Keys" %><%--
  Created by IntelliJ IDEA.
  User: davel
  Date: 06.05.2018
  Time: 22:06
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
        <%
            if( session.getAttribute(Keys.LOGINKEY) == null || ((String) session.getAttribute(Keys.LOGINKEY)).isEmpty() ) {
                response.sendRedirect("index.jsp?sessionExpired=TRUE");
                return;
            }
        %>

        <c:if test="${param.errMsg == 'EMPTY_FIELD'}">
            <p class="errorTextRegistration">Some fields were left unfilled.</p>
        </c:if>

        <c:if test="${param.errMsg == 'ALREADY_EXISTS'}">
            <p class="errorTextRegistration">Question already exists.</p>
        </c:if>

        <div class="regBox" style="width: 900px; margin-top: 15%;">
            <form action ="AddQuestionsServlet" method="post">
                <input class="regInput" type="text" name="questionInput" id="questionInput" style="width: 900px;" value="FILL QUESTION IN HERE" onblur=" if (this.value === '') {this.value = 'FILL QUESTION IN HERE';} theBlur(this);" onfocus="if (this.value === 'FILL QUESTION IN HERE') {this.value = '';} theFocus(this);">
                <input class="regInput" type="text" name="answerFirst" id="answerFirst" style="width: 440px; float: left;" value="FIRST ANSWER" onblur=" if (this.value === '') {this.value = 'FIRST ANSWER';} theBlur(this);" onfocus="if (this.value === 'FIRST ANSWER') {this.value = '';} theFocus(this)">
                <input class="regInput" type="text" name="answerSecond" id="answerSecond" style="width: 440px; float: right;" value="SECOND ANSWER" onblur=" if (this.value === '') {this.value = 'SECOND ANSWER';} theBlur(this);" onfocus="if (this.value === 'SECOND ANSWER') {this.value = '';} theFocus(this)">
                <input class="regInput" type="text" name="answerThird" id="answerThird" style="width: 440px; float: left;" value="THIRD ANSWER" onblur=" if (this.value === '') {this.value = 'THIRD ANSWER';} theBlur(this);" onfocus="if (this.value === 'THIRD ANSWER') {this.value = '';} theFocus(this)">
                <input class="regInput" type="text" name="answerFourth" id="answerFourth" style="width: 440px; float: right;" value="FOURTH ANSWER" onblur=" if (this.value === '') {this.value = 'FOURTH ANSWER';} theBlur(this);" onfocus="if (this.value === 'FOURTH ANSWER') {this.value = '';} theFocus(this)">
                <fieldset style="overflow: hidden;">
                    <div style="float: left; clear: none;">
                        <input type="radio" name="correctAnswer" value="FIRST" id="FIRST">
                        <label for="FIRST">FIRST</label>
                        <input type="radio" name="correctAnswer" value="SECOND" id="SECOND">
                        <label for="SECOND">SECOND</label>
                        <input type="radio" name="correctAnswer" value="THIRD" id="THIRD">
                        <label for="THIRD">THIRD</label>
                        <input type="radio" name="correctAnswer" value="FOURTH" id="FOURTH">
                        <label for="FOURTH">FOURTH</label>
                    </div>
                </fieldset>
                <input class="gamePinSubmit" type="submit" value="ADD QUESTION" name="submit" id="submit" style="width: 900px; margin-bottom: 3px;">
            </form>
            <form action="createCustomGame.jsp" method="post">
                <input class="gamePinSubmit" type="submit" value="PREVIOUS PAGE" style="width: 900px; margin-top: 3px;">
            </form>
        </div>
    </body>
</html>
