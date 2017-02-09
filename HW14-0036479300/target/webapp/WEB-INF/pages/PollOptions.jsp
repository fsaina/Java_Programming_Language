<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="hr.fer.zemris.java.tecaj_13.model.PollOptionsEntry"%>
<%@page import="java.util.List"%>
<%
    List<PollOptionsEntry> unosi = (List<PollOptionsEntry>)request.getAttribute("pollOptions");
%>
<html>
<body>

<b>Pronađeni su sljedeći unosi:</b><br>

<% if(unosi.isEmpty()) { %>
Nema unosa.
<% } else { %>
<ul>
    <% for(PollOptionsEntry u : unosi) { %>
    <li>[ID=<%= u.getTitle() %>] <%= u.getLink() %> </li>
    <% } %>
</ul>
<% } %>

</body>
</html>
