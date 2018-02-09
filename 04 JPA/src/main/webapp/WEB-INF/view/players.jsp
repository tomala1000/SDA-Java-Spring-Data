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
<%--@elvariable id="playerSession" type="com.kchmielewski.sda.java.spring01java.data.player.web.PlayerSession"--%>
<c:if test="${playerSession.counter > 0}">
    <p>
        You have added ${playerSession.counter} players in total and most recent one
        is ${playerSession.mostRecentPlayer}
    </p>
</c:if>
<h2>Adding and removing</h2>
<form:form method="post" modelAttribute="player">
    <ul>
        <c:forEach items="${players}" var="player">
            <li>
                <a href="/players/edit/${player.id}">${player.name} ${player.surname}</a>
                <button type="submit" name="removeId" value="${player.id}">Remove</button>
            </li>
        </c:forEach>
        <label><spring:message code="players.name"/>:<form:input path="name" type="text"/></label>
        <label><spring:message code="players.surname"/>:<form:input path="surname" type="text"/></label>
        <button type="submit" name="action" value="ADD">Add</button>
    </ul>
    <form:errors path="name"/>
    <form:errors path="surname"/>
</form:form>
</body>
</html>
