<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Files</title>
    </head>
    <body>
        <form action="file-browser" method="POST">
            <input type="submit" value="Выход" id="logout">
        </form>
        <style type="text/css">
            #logout {
                float: right;
                width: 200px;
                height: 50px;
                font-size: 18px;
            }
        </style>
        <p><%= request.getAttribute("timestamp") %></p>
        <h2><%= ((java.io.File) request.getAttribute("currentDirectory")).getPath() %></h2>
        <%
            java.io.File currentDirectory = (java.io.File) request.getAttribute("currentDirectory");
            if (currentDirectory != null && currentDirectory.getParent() != null) {
            %>
            <a href="file-browser?path=<%= currentDirectory.getParent() %>">Вверх</a><br><br>
            <%
            }
            %>
        <ul>
        <%
        java.io.File[] directories = (java.io.File[]) request.getAttribute("directories");
        if (directories != null) {
            for (java.io.File file : directories) {
                %>
                <li>
                &#128194; <a href="file-browser?path=<%= file.getPath() %>"><%= file.getName() %></a>
                </li>
                <%
            }
        }
        %>
        <%
        java.io.File[] files = (java.io.File[]) request.getAttribute("files");
        if (files != null) {
            for (java.io.File file : files) {
                %>
                <li>
                &#128190; <a href="download?file=<%= file.getPath() %>"><%= file.getName() %></a>
                </li>
                <%
            }
        }
        %>
        </ul>
    </body>
</html>