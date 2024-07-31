<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>Register Page</h1>


<form action="/todo/register" method="post">
<div class="mb-3">
    <label class="form-label">Title</label>
    <input type="text" name="title" class="form-control"  placeholder="title">
</div>
<div class="mb-3">
    <label class="form-label">Writer</label>
    <input type="text" name="writer" class="form-control" rows="2">
</div>
    <div class="mb-3">
        <button type="submit" name="writer" class="btn btn-primary">SAVE</button>
    </div>
</form>

<%@ include file="../includes/footer.jsp" %>