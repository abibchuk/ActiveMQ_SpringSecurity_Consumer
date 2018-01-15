<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="random" class="java.util.Random" scope="application" />
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Get Message</title>
</head>
<body>
<form:form method="POST">
    <table>
        <tr>
            <td colspan="2">
                <input type="submit" value="Get Message" />
            </td>
        </tr>
        <tr>
            <td>Received message: ${msg}</td>
        </tr>
    </table>
</form:form>
<a href="<c:url value="/logout" />">Logout</a>
</body>
</html>
