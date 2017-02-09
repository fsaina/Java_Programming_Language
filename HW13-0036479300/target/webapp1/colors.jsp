<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";
%>
<html>
<head>
    <title>Color chooser</title>
</head>
<body bgcolor= <%= bgColor %>>
<h3>Please select a color:</h3>
<a href="setColor?color=FFFFFF">White</a>
<a href="setColor?color=FF0000">Red</a>
<a href="setColor?color=008000">Green</a>
<a href="setColor?color=00FFFF">Cyan</a>
</body>
</html>
