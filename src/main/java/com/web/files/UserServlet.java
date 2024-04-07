package com.web.files;

import com.web.files.Model.UserProfile;
import com.web.files.Service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

import static com.web.files.Service.CookieService.GetCookie;

@WebServlet(urlPatterns = {"/reg"})
public class UserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {
        Cookie cookie = GetCookie(request,"Auth");
        if (!cookie.getValue().equals("")) {
            String currentURL = request.getRequestURL().toString();
            response.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/file-browser");
            return;
        }
        request.getRequestDispatcher("reg.jsp").forward(request, response);
    }

    //Регистрация в системе
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String pass = request.getParameter("pass");

        if (email.isEmpty() || login.isEmpty() || pass.isEmpty()) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Отсутсвует email, логин или пароль");
            return;
        }

        UserProfile profile = new UserProfile(login, pass, email);
        if (AccountService.getUserByLogin(login) == null) {


            // Создание новой папки для пользователя
            File folder = new File("C:\\JavaTechDB",login);
            boolean isCreationSuccess = folder.mkdir();
            //Скорее всего никогда не будет false, но если будет нехватать памяти или что-то ещё, то сработает
            if (!isCreationSuccess) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().println("Случилась ошибка при создании папки, попробуйте ещё раз");
                return;
            }

            AccountService.addNewUser(profile);

            request.getSession().setAttribute("login",login);
            request.getSession().setAttribute("pass",pass);
            String currentURL = request.getRequestURL().toString();
            response.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/auth");
        } else {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println("Пользователь с таким логином уже есть в системе");
        }
    }
}
