<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../includes/header.jsp"%>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>Edit Page</h1>

${todo}


<form action = "/todo/edit" method="post">
    <div>
        <label>TNO</label>
        <input type="text" name="tno" value="${todo.tno}" readonly> <!-- 수정할게 아니니까 readonly-->
    </div>
    <div>
        <label>TITLE</label>
        <input type="text" name="title" value="${todo.title}">
    </div>
    <div>
        <label>WRITER</label>
        <input type="text" name="writer" value="${todo.writer}">
    </div>
    <div>
        <button>MODIFY</button>
    </div>
</form>

<form action = "/todo/remove" method="post">
    <input type="hidden" name="tno" value="${todo.tno}">
    <button>REMOVE</button>
</form>

<%@include file="../includes/footer.jsp"%>

