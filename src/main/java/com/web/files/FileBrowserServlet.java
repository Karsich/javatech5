package com.web.files;

import com.web.files.Service.AccountService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class FileBrowserServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = (String)request.getSession().getAttribute("login");
        String pass = (String)request.getSession().getAttribute("pass");


        if (AccountService.getUserByLogin(login)==null || !AccountService.getUserByLogin(login).getPass().equals(pass)) {
            String currentURL = request.getRequestURL().toString();
            response.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/auth");
            return;
        }
        Cookie loginCookie = new Cookie("login", login);
        loginCookie.setPath("/");
        loginCookie.setMaxAge(60 * 60 * 24 * 365); // Set cookie expiration to 1 year
        response.addCookie(loginCookie);

        String path = request.getParameter("path");
        if (path == null || path.equals("C:\\JavaTechDB") )
            path = "C:\\JavaTechDB\\" + login;

        File directory = new File(path);

        File[] allFiles = directory.listFiles();
        if (allFiles!=null) {
            request.setAttribute("files", Arrays.stream(allFiles).filter(File::isFile).toArray(File[]::new));
            request.setAttribute("directories",Arrays.stream(allFiles).filter(File::isDirectory).toArray(File[]::new));
        }
        else {
            request.setAttribute("files", null);
            request.setAttribute("directories",null);
        }

        request.setAttribute("currentDirectory", directory);
        request.setAttribute("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        request.getRequestDispatcher("fileBrowser.jsp").forward(request, response);
    }
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {
        httpServletRequest.getSession().removeAttribute("login");
        httpServletRequest.getSession().removeAttribute("pass");

        String currentURL = httpServletRequest.getRequestURL().toString();
        httpServletResponse.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/auth");
    }

    @Override
    public void destroy() {
    }
}
