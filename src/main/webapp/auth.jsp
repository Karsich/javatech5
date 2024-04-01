<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Authorization</title>
</head>
<body>
<p>Авторизация</p>

<form action="auth" method="POST">
    Login: <input type="text" name="login"/>
    Password: <input type="password" name="pass"/>
    <input type="submit" value="Войти">
</form>
<a href="reg">Зарегистрироваться</a>
</body>
</html>
