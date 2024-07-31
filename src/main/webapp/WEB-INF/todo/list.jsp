<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../includes/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

  <h1>List Page</h1>
<br>
<form action="/todo/register" method = get>
<button type="submit" class="btn btn-warning">GOGO Register </button>
</form>
<br>

<c:forEach items = "${todoList}" var = "todo">
<ul>
  <li>
    <div> Tno : ${todo.tno}</div>
    <div><a href="/todo/get?tno=${todo.tno}">Title: ${todo.title}</a></div>
  </li>
</ul>
</c:forEach>

<ul class="pagination">

  <c:if test="${pageInfo.prev}">
  <li class="page-item">
    <a class="page-link" href="/todo/list?page=${pageInfo.start-1}">Previous</a>
  </li>
  </c:if>

  <c:forEach begin="${pageInfo.start}" end="${pageInfo.end}" var="num">
  <li class="page-item ${pageInfo.page==num ? 'active' : ' '} "><a class="page-link" href="/todo/list?page=${num}">${num}</a></li>
  </c:forEach>

<c:if test="${pageInfo.next}">
  <li class="page-item">
    <a class="page-link" href="/todo/list?page=${pageInfo.end+1}">Next</a>
  </li>
</c:if>

</ul>


<%@ include file="../includes/footer.jsp" %>
