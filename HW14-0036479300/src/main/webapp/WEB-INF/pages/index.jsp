<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@ page import="hr.fer.zemris.java.tecaj_13.model.PollEntry" %>
<%
    List<PollEntry> entry = (List<PollEntry>)request.getAttribute("polls");
%>
<html>
<body>

<b>Pronađene su sljedeće ankete:</b><br>

<% if(entry.isEmpty()) { %>
Nema unosa.
<% } else { %>
<ul>
    <% for(PollEntry u : entry) { %>
    <a href="glasanje?pollID=<%=u.getId()%>"><%=u.getTitle()%></a>
    <% } %>
</ul>
<% } %>

</body>
</html>
