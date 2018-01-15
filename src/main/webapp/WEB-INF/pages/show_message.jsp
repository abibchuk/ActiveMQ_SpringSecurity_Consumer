<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="random" class="java.util.Random" scope="application" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Message</title>
</head>
<body>
<table>
<c:forEach items="${list}" var="msg">
    <tr>
        <td>Received message: ${msg}</td>
    </tr>
</c:forEach>
</table>
<a href="<c:url value="/logout" />">Logout</a>
</body>
</html>