<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Get Page</h1>

${todo}

<br>

<form action="/todo/edit?tno=${todo.tno}" method = get>
    <input type="hidden" name="tno" value="${todo.tno}">
    <button type="submit" class="btn btn-warning">GOGO Modify / Delete </button>
</form>

<%@ include file="../includes/footer.jsp" %>