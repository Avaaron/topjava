<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Users</h2>
<c:set var="us" value="${user}"/>
<p>Вы вошли как пользователь ${us}</p>
<a href="meals">Перейти к списку еды</a>
</body>
</html>