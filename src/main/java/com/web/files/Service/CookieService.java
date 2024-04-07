package com.web.files.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.files.Model.UserProfile;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.Map;

public class CookieService {

    public static Cookie MakeCookieAuth(UserProfile userProfiles) {
        ObjectMapper objectMapper = new ObjectMapper();
        String dbAsString;
        try {
            dbAsString = objectMapper.writeValueAsString(userProfiles);
            Cookie cookie = new Cookie("Auth", dbAsString.replaceAll("\"", "\'"));
            return cookie;
        } catch (JsonProcessingException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return new Cookie("Auth",null);
    }
    public static Cookie GetCookie(HttpServletRequest request,String cookieName) {
        Cookie[] cookies = request.getCookies();

        Cookie cookie = null;
        if(cookies !=null) {
            for(Cookie c: cookies) {
                if(cookieName.equals(c.getName())) {
                    cookie = c;
                    break;
                }
            }
        }
        return cookie;
    }
}
