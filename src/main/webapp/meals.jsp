<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
    </tr>
    <c:forEach var="meal" items="${ mealWithExceeds }">
        <fmt:parseDate value="${ meal.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedTime" type="both" />
        <c:set var="color" value="${ meal.exceed ? 'red' : 'green' }" />
            <tr style="color: ${color}">
                <td>
                    <fmt:formatDate pattern="dd.MM.yyy HH:mm" value="${ parsedTime }" />
                </td>
                <td>
                    ${ meal.description }
                </td>
                <td>
                    ${ meal.calories }
                </td>
            </tr>
    </c:forEach>

</table>
</body>
</html>
