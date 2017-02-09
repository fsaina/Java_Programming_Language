<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
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

<h1 style="text-align: center; color: red"> ${message}</h1>

<%--back button functionality--%>
<div style="text-align: center">
    <button style="align-items: center;" onclick="goBack()">Go Back</button>
</div>
<script>
    function goBack() {
        window.history.back();
    }
</script>

</body>
</html>
