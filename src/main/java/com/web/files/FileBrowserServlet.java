package com.web.files;

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
import java.util.regex.Pattern;

import static com.web.files.Service.CookieService.GetCookie;

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
        Cookie cookie = GetCookie(request,"Auth");
        if (login==null) login = cookie.getValue();


        //if (AccountService.getUserByLogin(login)==null || !AccountService.getUserByLogin(login).getPass().equals(pass) || cookie.getValue()==null) {
        if (cookie.getValue().equals("")) {
            String currentURL = request.getRequestURL().toString();
            response.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/auth");
            return;
        }

        String path = request.getParameter("path");
        String pattern = "^C:\\\\JavaTechDB\\\\[a-zA-Z_0-9]+[\\\\/].*$";
        if (path == null || !Pattern.matches(pattern, path) )
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

        Cookie cookie = new Cookie("Auth","");
        cookie.setMaxAge(1000000);
        httpServletResponse.addCookie(cookie);

        String currentURL = httpServletRequest.getRequestURL().toString();
        httpServletResponse.sendRedirect(currentURL.substring(0, currentURL.lastIndexOf("/")) + "/auth");
    }

    @Override
    public void destroy() {
    }
}
