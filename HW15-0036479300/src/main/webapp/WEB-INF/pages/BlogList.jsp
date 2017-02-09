<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String loggedNick = (String) session.getAttribute("current.user.nick");
    String currentNick = (String) request.getAttribute("nickname");
%>
<html>
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

<h3>List of blog entries <%=currentNick%> created:</h3>

<c:choose>
    <c:when test="${blogEntrys==null}">
        There are no blog entries!
    </c:when>
    <c:otherwise>

        <c:forEach var="blogEntry" items="${blogEntrys}">
            <div style="text-align: center;background: aquamarine;">
                <h1><c:out value="${blogEntry.title}"/></h1>
                <p><c:out value="${blogEntry.text}"/></p>

                    <%--owners can edit--%>
                <c:if test="<%=loggedNick != null && loggedNick.equals(currentNick)%>">
                    <a href="${pageContext.request.contextPath}/servleti/author/${nickname}/edit/${blogEntry.id}">Edit</a>
                </c:if>

                    <%--logged in users can comment--%>
                <c:if test="<%=loggedNick != null%>">
                    <a href="${pageContext.request.contextPath}/servleti/author/${nickname}/${blogEntry.id}">Comment</a>
                </c:if>

            </div>
        </c:forEach>

    </c:otherwise>
</c:choose>

<br>
<br>

<c:if test="<%=loggedNick != null && loggedNick.equals(currentNick)%>">
    <a style="background: burlywood"
       href="${pageContext.request.contextPath}/servleti/author/${nickname}/new">Add a new blog entry</a>
</c:if>


</body>
</html>
