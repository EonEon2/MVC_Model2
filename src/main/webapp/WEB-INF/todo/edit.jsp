<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Edit Page</h1>

<form action = "/todo/edit" method="post">
    <div>
        <lable>TNO</lable>
        <input type="text" name="tno" value="${todo.tno}" readonly>
    </div>

    <div>
        <lable>TITLE</lable>
        <input type="text" name="title" value="${todo.title}">
    </div>

    <div>
        <label>WRITER</label>
        <input type="text" name="writer" value="${todo.writer}">
    </div>

    <div>
        <button>EDIT</button>
    </div>
</form>





<form action="/todo/remove" method ="post">
    <input type="hidden" name="tno" value="${todo.tno}">
    <button>REMOVE</button>
</form>


<%@ include file="../includes/footer.jsp" %>