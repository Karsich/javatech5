package com.web.files;

import com.web.files.Model.UserProfile;
import com.web.files.Service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/auth"})
public class SessionsServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("auth.jsp").forward(httpServletRequest, httpServletResponse);
    }

    //Вход в систему
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {
        String login = httpServletRequest.getParameter("login");
        String pass = httpServletRequest.getParameter("pass");

        if (login.isEmpty() || pass.isEmpty()) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Отсутсвует логин или пароль");
            return;
        }

        UserProfile profile = AccountService.getUserByLogin(login);
        if (profile == null || !profile.getPass().equals(pass)) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Неправильный логин или пароль");
            return;
        }

        httpServletRequest.getSession().setAttribute("login",login);
        httpServletRequest.getSession().setAttribute("pass",pass);

        String currentURL = httpServletRequest.getRequestURL().toString();
        httpServletResponse.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/file-browser");
    }

}
