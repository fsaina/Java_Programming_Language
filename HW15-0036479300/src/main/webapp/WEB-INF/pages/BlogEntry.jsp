<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String nicknameLogged = (String) session.getAttribute("current.user.nick");
%>
<html>
<head>
    <title>Blog entry maker</title>
</head>
<body>

<%--Page header, login information--%>
<div style="color: white;height: 100px;background: rgb(128, 128, 128);margin: 0px auto 20px;text-align: center;">
    <h3>Welcome to Blog</h3>
    <c:choose>
        <c:when test="<%=session.getAttribute(\"current.user.id\") != null%>">
            <p>You are logged in
                as: <%=session.getAttribute("current.user.fn")%> <%=session.getAttribute("current.user.ln")%>
            </p>
            <a href="${pageContext.request.contextPath}/session/logout">Logout</a>
        </c:when>
        <c:otherwise>
            <p>Not logged in</p>
        </c:otherwise>
    </c:choose>
</div>

<form id="SubmitBlogEntry" action="${pageContext.request.contextPath}/servleti/entry/post" method="post">
    <fieldset>
        <ol>
            <li>
                <label>Title</label>
                <input name="title" value="${title}" id="title" type="text">
            </li>
            <li>
                <label>Text:</label>
                <textarea name="text" cols="40" rows="5" >${text}</textarea>
            </li>
            <input type="hidden" name="id" value="${idNumber}">
            <li class="formSubmit">
                <input value="Submit" type="submit">
            </li>
        </ol>
    </fieldset>
    <span style="color: red" class="message">${message}</span>
</form>

</body>
</html>
