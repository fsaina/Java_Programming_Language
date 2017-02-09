<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String loggedNick = (String) session.getAttribute("current.user.nick");
    String currentNick = (String) request.getAttribute("nickname");
%>
<html>
<head>
    <title>Blog entry view</title>
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

<c:choose>
    <c:when test="${blogEntry==null}">
        Nema unosa!
    </c:when>
    <c:otherwise>
        <div id="blog_text" style="text-align: center;background: aquamarine;">
            <h1><c:out value="${blogEntry.title}"/></h1>
            <p><c:out value="${blogEntry.text}"/></p>
            <p><c:out value="Author: ${blogEntry.creator.nick}"/></p>
            <p><c:out value="Created: ${blogEntry.createdAt}"/></p>
            <p><c:out value="Last modified: ${blogEntry.lastModifiedAt}"/></p>


                <%--owners can edit--%>
            <c:if test="<%=loggedNick != null && loggedNick.equals(currentNick)%>">
                <br>
                <br>
                <a href="${pageContext.request.contextPath}/servleti/author/${nickname}/edit/${blogEntry.id}">Edit</a>
                <br>
            </c:if>
        </div>
        <div id="comments" style="text-align: left">
            <c:if test="${!blogEntry.comments.isEmpty()}">
                <ul>
                    <c:forEach var="e" items="${blogEntry.comments}">
                        <li>
                            <div style="font-weight: bold">[Korisnik=<c:out value="${e.usersEMail}"/>] <c:out
                                    value="${e.postedOn}"/></div>
                            <div style="padding-left: 10px;"><c:out value="${e.message}"/></div>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
    </c:otherwise>
</c:choose>

<form id="Comment" action="${pageContext.request.contextPath}/servleti/comment/post" method="post">
    <div style="text-align: center">
        <ol>
            <li>
                <label>Comment:</label>
                <textarea style="width: 100%" name="comment" cols="40" rows="5">${text}</textarea>
            </li>
            <input type="hidden" name="idEntry" value="${blogEntry.id}">
            <input type="hidden" name="nickname" value="${nickname}">
            <li class="formSubmit">
                <input value="Submit" type="submit">
            </li>
        </ol>
    </div>
    <span style="color: red" class="message">${message}</span>
</form>

</body>
</html>
