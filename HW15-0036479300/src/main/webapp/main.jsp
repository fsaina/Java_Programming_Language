<%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogUser" %>
<%@ page import="java.util.List" %>
<%@ page import="hr.fer.zemris.java.tecaj_13.dao.DAOProvider" %>
<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    List<BlogUser> authors = DAOProvider.getDAO().getAuthors();
%>
<html>
<head>
    <title>Main page</title>
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

<form id="Login" action="${pageContext.request.contextPath}/servleti/main" method="post">
    <fieldset style="text-align: center;">
        <ol>
            <li>
                <label for="Login_username">Nickname</label>
                <input name="nickname" value="" id="Login_username" type="text">
            </li>
            <li>
                <label for="Login_password">Password</label>
                <input name="password" id="Login_password" type="password">

            </li>
            <li class="formSubmit">
                <input id="Login_0" value="Submit" type="submit">
            </li>
        </ol>
    </fieldset>
    <span style="color: red" class="message">${message}</span>
</form>

<a href="${pageContext.request.contextPath}/register.jsp">Click here to register as a new user</a>

<c:choose>
    <c:when test="<%=authors.isEmpty()%>">
        No authors yet!
    </c:when>
    <c:otherwise>
        <ul>
            <c:forEach var="e" items="<%=authors%>">
                <li><a
                        href="${pageContext.request.contextPath}/servleti/author/${e.nick}"
                        target="_blank">${e.nick}</a></li>
            </c:forEach>
        </ul>

    </c:otherwise>
</c:choose>

<%--home link--%>
<div style="text-align: center">
    <a href="${pageContext.request.contextPath}/servleti/main">Back to login page</a>
</div>

</body>
</html>
