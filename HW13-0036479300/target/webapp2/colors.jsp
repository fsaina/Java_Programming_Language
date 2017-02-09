<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<%
    String bgColor = (String) session.getAttribute("pickedBgCol");
    if (bgColor == null) bgColor = "#FFFFFF";
%>
<html>
<head>
    <title>Color chooser</title>
</head>
<body style="text-align: center" bgcolor= <%= bgColor %>>
<STYLE type="text/css">

    a {
        height: 100%; width: 100%; text-align: center;
        font-size: 20px;
    }

    td{
        height: 100px;
    }

</STYLE>
<h3>Please select a color:</h3>

<table style="width:100%">
    <tr>
        <td>
            <a href="setColor?color=FFFFFF">
                <div style="height:100%;width:100%;background-color: #FFFFFF;">
                    White
                </div>
            </a>
        </td>
        <td>
            <a href="setColor?color=FF0000">
                <div style="height:100%;width:100%;background-color: #FF0000;">
                    Red
                </div>
            </a>
        </td>
    </tr>
    <tr>
        <td>
            <a href="setColor?color=008000">
                <div style="height:100%;width:100%;background-color: #008000;">
                    Green
                </div>
            </a>
        </td>
        <td>
            <a href="setColor?color=00FFFF">
                <div style="height:100%;width:100%;background-color: #00FFFF;">
                    Cyan
                </div>
            </a>
        </td>
    </tr>
</table>

</body>
</html>
