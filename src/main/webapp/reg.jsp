<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Registration</title>
</head>
<body>
<p>Регистрация</p>

<form action="reg" method="POST">
    Email: <input type="text" name="email"/>
    Login: <input type="text" name="login"/>
    Password: <input type="password" name="pass"/>
    <input type="submit" value="Зарегистрироваться">
</form>
<a href="auth">Войти, если уже зарегистрирован.</a>
</body>
</html>
