<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../includes/header.jsp"%>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>

${todo}

<%--<a href="/todo/edit?tno=${todo.tno}">Modify/Delete</a>--%>
<br>
<form action ="/todo/edit?tno=${todo.tno}" method="get">
    <input type="hidden" name="tno" value="${todo.tno}">
    <button type="submit" name="tno" class="btn btn-warning" onclick="submitForm()">Modify/Deletery</button>
</form>

<br><br>


<script>
    function submitForm() {
        document.getElementById("editForm").submit();
    }
</script>

<%@include file="../includes/footer.jsp"%>

