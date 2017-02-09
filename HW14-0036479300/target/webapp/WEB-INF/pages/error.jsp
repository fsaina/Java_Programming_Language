<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";
%>
<html>
<head>
    <title>Error page</title>
</head>
<body style="text-align: center;" bgcolor= <%= bgColor %> >
<h1>There has been an error</h1>
<br/>
<br/>
<p style="font-size: 30px;color: white;background-color: red;padding: 40px;" >${errorMessage}</p>
</body>
</html>
