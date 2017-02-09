<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";
%>
<html>
<head>
    <title>Trigonometry tables</title>
</head>

<body bgcolor= <%= bgColor %>>

<h3>Sin(x) values from ${startValue} to ${endValue} degrees:</h3>

<table style="width:30%">

    <c:forEach var="i" begin="0" end="${fn:length(sinValues) - 1}">
        <tr>
            <td>${i + startValue}</td>
            <td>${sinValues[i]}</td>
        </tr>
    </c:forEach>

</table>

<h3>Cos(x) values from ${startValue} to ${endValue} degrees:</h3>

<table style="width:30%">

    <c:forEach var="i" begin="0" end="${fn:length(cosValues) - 1}">
        <tr>
            <td>${i + startValue}</td>
            <td>${cosValues[i]}</td>
        </tr>
    </c:forEach>

</table>

<body/>
