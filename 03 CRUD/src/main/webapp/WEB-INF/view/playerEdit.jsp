<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--@elvariable id="players" type="java.util.List"--%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello World</title>
</head>
<body>
<h2>Editing</h2>
<h3><a href="/players">Go back</a></h3>
<form:form method="post" modelAttribute="player" action="/players">
    <ul>
        <label><spring:message code="players.name"/>:<form:input path="name" type="text"/></label>
        <label><spring:message code="players.surname"/>:<form:input path="surname" type="text"/></label>
        <button type="submit" name="editId" value="${player.id}">Edit</button>
    </ul>
    <form:errors path="name"/>
    <form:errors path="surname"/>
</form:form>
</body>
</html>
