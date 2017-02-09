<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";

    ArrayList<String> colors = new ArrayList<String>();
    colors.add("yellow");
    colors.add("blue");
    colors.add("green");
    colors.add("red");
    int index = new Random().nextInt(colors.size());
%>
<html>
<head>
    <style type="text/css">
        table.rez td {
            text-align: center;
        }
    </style>
</head>
<body bgcolor= <%= bgColor %>>

<font color=<%= colors.get(index) %>>Quick Spock! Grab your lightsaber Dr.Who is coming!</font>

</body>
</html>
