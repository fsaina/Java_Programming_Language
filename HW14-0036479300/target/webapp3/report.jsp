<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";
%>
<html>
<head>
    <title>Report page</title>
</head>
<body bgcolor= <%= bgColor %>>

<h1>OS usage</h1>
<p>Here are the results of OS usage in survey that we completed.</p>

<img alt="Pie-chart" src="reportImage" width="700" height="400"/>

</body>
</html>
