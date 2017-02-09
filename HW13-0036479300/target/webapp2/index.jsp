<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";
%>
<html>
<head>
    <title>Index page</title>
</head>
<body bgcolor= <%= bgColor %>>
<h4>Color chooser (problem 1):</h4>
<a href="colors.jsp">Background color chooser</a><br/><br/>
<h4>Trinognometry calculator (problem 2):</h4>
<a href="trigonometry?a=0&b=90">Trigonometric print-out 0-90 degrees</a><br/><br/>
<h4>Funny story (problem 3):</h4>
<a href="stories/funny.jsp">Funny story page</a><br/><br/>
<h4>Report page (problem 4):</h4>
<a href="report.jsp">Bar chart image</a><br/><br/>
<h4>Powers page (problem 5) (save as .xls):</h4>
<a href="powers?a=1&b=100&n=3">XLS power documents</a><br/><br/>
<h4>Running time (problem 6):</h4>
<a href="appinfo.jsp">Server time running</a>
</body>
</html>
