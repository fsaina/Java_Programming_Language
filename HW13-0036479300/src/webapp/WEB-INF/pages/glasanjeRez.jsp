<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";
%>
<html>
<head>
    <title>Glasanje rezulati</title>
</head>
<body bgcolor= <%= bgColor %>>
<h1>Rezultati glasanja</h1>
<p>Ovo su rezultati glasanja.</p>
<table border="1" cellspacing="0" class="rez">
    <thead>
    <tr>
        <th>Bend</th>
        <th>Broj glasova</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="i" begin="0" end="${fn:length(bands) -1}">
        <tr>
            <td>${bands[i].name}</td>
            <td>${bands[i].votes}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<h2>Grafički prikaz rezultata</h2>
<img alt="Pie-chart" src="glasanje-grafika" width="700" height="400"/>
<h2>Rezultati u XLS formatu</h2>
<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a></p>
<h2>Razno</h2>
<p>Primjeri pjesama pobjedničkih bendova:</p>
<ul>
    <c:forEach var="i" begin="0" end="1">
        <li><a href="${bands[i].link}" target="_blank">${bands[i].name}</a></li>
    </c:forEach>
</ul>
</body>
</html>
