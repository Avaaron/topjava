<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <h3>Фильтр по дате и времени</h3>
    <c:set var="startDate" value="${startDate}" />
    <c:set var="endDate" value="${endDate}" />
    <c:set var="startTime" value="${startTime}" />
    <c:set var="endTime" value="${endTime}" />
    <form method="get" name="timeFilter">
        <table>
            <tr>
                <td>
                    От даты
                </td>
                <td>
                    От времени
                </td>
            </tr>
            <tr>
                <td>
                    <input type="date" name="startDate" value="${startDate}"/>
                </td>
                <td>
                    <input type="time" name="startTime" value="${startTime}"/>
                </td>
            </tr>
            <tr>
                <td>
                    До даты
                </td>
                <td>
                    До времени
                </td>
            </tr>
            <tr>
                <td>
                    <input type="date" name="endDate" value="${endDate}" />
                </td>
                <td>
                    <input type="time" name="endTime" value="${endTime}"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="submit" name="sendDateFilter" value="Применить">

                </td>
                <td>
                    <input type="submit" name="clean" value="Очистить">
                </td>
            </tr>
        </table>
    </form>
    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=DateTimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>