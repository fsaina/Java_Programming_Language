<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";

    ServletContext context = getServletConfig().getServletContext();
    long startTime = (Long) context.getAttribute("startTime");
    long currentTime = System.currentTimeMillis();
    long difference = currentTime - startTime;

    long milisec = difference % 1000;
    long second = (difference / 1000) % 60;
    long minute = (difference / (1000 * 60)) % 60;
    long hour = (difference / (1000 * 60 * 60)) % 24;
    long days = hour / 24;

    String timeRunningFormatted = String.format(" %d days\n %d hours\n %02d minutes\n %02d seconds\n %03d miliseconds", days, hour, minute, second, milisec);
%>
<html>
<head>
    <title>Web app time running</title>
</head>
<body bgcolor= <%= bgColor %>>

<h3>
    Time elapsed from starting the server:
</h3>

<p style="text-align: left;font-size: 70px;white-space: pre;"><%= timeRunningFormatted %></p>
</body>
</html>
