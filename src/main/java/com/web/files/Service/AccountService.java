package com.web.files.Service;

import com.web.files.Model.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    //Сервис, что следит за регистрацией пользователей
    private static final Map<String, UserProfile> loginToProfile = new HashMap<>() {
        //put("admin", new UserProfile("admin", "123", "AAAAAAAAAAAAAAA"));
    };

    public static void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public static UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }


}
