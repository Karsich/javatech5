package com.web.files;

import com.web.files.Model.UserProfile;
import com.web.files.Service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = {"/reg"})
public class UserServlet extends HttpServlet {

    public void doGet(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("reg.jsp").forward(httpServletRequest, httpServletResponse);
    }

    //Регистрация в системе
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {
        String email = httpServletRequest.getParameter("email");
        String login = httpServletRequest.getParameter("login");
        String pass = httpServletRequest.getParameter("pass");

        if (email.isEmpty() || login.isEmpty() || pass.isEmpty()) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Отсутсвует email, логин или пароль");
            return;
        }

        UserProfile profile = new UserProfile(login, pass, email);
        if (AccountService.getUserByLogin(login) == null) {
            AccountService.addNewUser(profile);

            httpServletRequest.getSession().setAttribute("login",login);
            httpServletRequest.getSession().setAttribute("pass",pass);

            // Создание новой папки для пользователя
            File folder = new File("C:\\JavaTechDB",login);
            boolean isCreationSuccess = folder.mkdir();
            //Скорее всего никогда не будет false, но если будет нехватать памяти или что-то ещё, то сработает
            if (!isCreationSuccess) {
                httpServletResponse.setContentType("text/html;charset=utf-8");
                httpServletResponse.getWriter().println("Случилась ошибка при создании папки, попробуйте ещё раз");
                return;
            }

            String currentURL = httpServletRequest.getRequestURL().toString();
            httpServletResponse.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/auth");
        } else {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Пользователь с таким логином уже есть в системе");
        }
    }
}
