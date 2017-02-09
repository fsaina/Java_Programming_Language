<%@ page session="true" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>

<div id="container">

    <form action="${pageContext.request.contextPath}/servleti/register" method="post">
        <h1>Register a new user:</h1>
        <div class="line"><label for="nickname">Nickname:</label><input type="text" name="nickname"
                                                                        id="nickname"/></div>
        <div class="line"><label for="pwd">Password:</label><input type="password" name="pwd" id="pwd"/></div>
        <div class="line"><label for="firstname">First name: </label><input type="text" name="firstname"
                                                                            id="firstname"/></div>
        <div class="line"><label for="surname">Surname: </label><input type="text" name="surname"
                                                                       id="surname"/></div>
        <div class="line"><label for="email">Email: </label><input type="email" name="email" id="email"/>
        </div>

        <input type="submit">
        <span style="color: blue" class="success">${infoMessage}</span>
        <span style="color: red" class="error">${errorMessage}</span>

        <p>Note: All fields are required!</p>

    </form>
</div>

<%--home link--%>
<div style="text-align: center">
    <a href="${pageContext.request.contextPath}/servleti/main">Back to login page</a>
</div>

</body>
</html>
