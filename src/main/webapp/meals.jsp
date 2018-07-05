<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <title>Meals</title>
    <style>
        .meal{
            border-collapse: collapse;
            border: 3px solid #808080;
            width: 600px;
            font-size: 14px;

        }

        .meal td{
            border: 1px solid #808080;
            text-align: center;
        }

        .meal caption{
            font-size: large;
        }

        .meal th{
            font-weight: normal;
            border: 1px solid #808080;
        }
    </style>

</head>
<body>

<table class="meal">
    <caption>Meals</caption>
    <tr>

        <th>Дата и время</th>
        <th>Описание</th>
        <th>Каллории</th>
        <th colspan="2">Операция</th>
    </tr>
    <c:forEach var="meal" items="${mealWithExceeds}">
        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedTime" type="both" />
        <c:set var="color" value="${meal.exceed ? 'red' : 'green' }" />
            <tr style="color: ${color}">
                <td>${meal.id}</td>
                <td>
                    <fmt:formatDate pattern="dd.MM.yyy HH:mm" value="${parsedTime}" />
                </td>
                <td>
                    ${meal.description}
                </td>
                <td>
                    ${meal.calories}
                </td>
                <td>
                    <a href="meals?act=edit&id=${meal.id}">Редактировать</a>
                </td>
                <td>
                    <a href="meals?act=delete&id=${meal.id}">Удалить</a>
                </td>
            </tr>
    </c:forEach>

</table>
<p></p>
<form method="post">
    <c:set var="m" value="${unit}" />
    <fmt:parseDate value="${m.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedTimeForEdit" type="both" />
    <input type="hidden" title="mealId" name="mealId" value="${m.id}" />
    <input type="text" title="dateTime" name="dateTime" value="<fmt:formatDate pattern="dd.MM.yyy HH:mm" value="${parsedTimeForEdit}" />" placeholder="Дата и время" />
    <input type="text" title="description" name="description" value="${m.description}"  placeholder="Описание" required />
    <input type="text" title="calories" name="calories"  value="${m.calories}" placeholder="Каллории" required />
    <c:set var="butCaption" value="${m.id == null ? 'Добавить' : 'Обновить' }" />
        <input type="submit" name="add" value="${butCaption}" />
    <input type="submit" name="clean" value="Очистить" />
</form>
<c:set var="mess" value="${message}" />
<p>${mess}</p>
</body>
</html>
