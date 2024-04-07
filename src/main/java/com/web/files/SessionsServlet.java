package com.web.files;

import com.web.files.Model.UserProfile;
import com.web.files.Service.AccountService;
import com.web.files.Service.CookieService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.web.files.Service.CookieService.GetCookie;

@WebServlet(urlPatterns = {"/auth"})
public class SessionsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = GetCookie(request,"Auth");
        if (!cookie.getValue().equals("")) {
            String currentURL = request.getRequestURL().toString();
            response.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/file-browser");
            return;
        }
        else request.getRequestDispatcher("auth.jsp").forward(request, response);
    }

    //Вход в систему
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        if (login.isEmpty() || pass.isEmpty()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Отсутсвует логин или пароль");
            return;
        }

        UserProfile profile = AccountService.getUserByLogin(login);
        if (profile == null || !profile.getPass().equals(pass)) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Неправильный логин или пароль");
            return;
        }

        Cookie cookie = new Cookie("Auth",login);
        cookie.setMaxAge(1000000);
        response.addCookie(cookie);

        request.getSession().setAttribute("login",login);
        request.getSession().setAttribute("pass",pass);



        String currentURL = request.getRequestURL().toString();
        response.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/file-browser");
    }

}
