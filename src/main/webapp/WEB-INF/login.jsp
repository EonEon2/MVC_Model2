<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-07-31
  Time: 오전 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/login" method="post">
        <div>
            <label>ID or Email</label>
        <input name="uid" type="text">
        </div>

        <div>
            <label>PassWord</label>
        <input name="upw" type="password">
        </div>

        <button>LOGIN</button>
    </form>

<div>
    <form action="/mregister">
        <button type="submit">Join</button>
    </form>
</div>
</body>
</html>
