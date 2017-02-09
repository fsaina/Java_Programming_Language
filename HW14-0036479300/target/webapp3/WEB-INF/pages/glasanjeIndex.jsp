<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";
%>
<html>
<head>
    <title>Glasanje</title>
</head>
<body bgcolor= <%= bgColor %>>
<h1>Glasanje za omiljeni bend:</h1>
<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
    glasali!</p>
<ol>

    <c:forEach var="i" begin="0" end="${fn:length(bandNames) -1}">
        <li><a href="glasanje-glasaj?id=${bandNames[i].id}">${bandNames[i].name}</a></li>
    </c:forEach>

</ol>
</body>
</html>