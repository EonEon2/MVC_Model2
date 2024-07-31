
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<style>
    h1{
        border-bottom: 3px solid grey;
        padding : 5px;
    }
</style>
    <h1>Member Register Page</h1>


<form action="/mregister" method="post">

    <div>
    <label>UserID</label>
    <input type="text" name="uid">
    </div>

    <div>
    <label>PassWord</label>
    <input type="password" name="upw">
    </div>

    <div>
    <label>UserEmail</label>
    <input type="text" name="email">
    </div>

    <button>Register</button>
</form>
</body>
</html>
