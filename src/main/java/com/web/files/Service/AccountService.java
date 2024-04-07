package com.web.files.Service;

import com.web.files.Model.UserProfile;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    //Сервис, что следит за регистрацией пользователей
    private static Map<String, UserProfile> loginToProfile = initHashMap();

    public static void addNewUser(UserProfile userProfile) {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public static UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }
    public static Map<String, UserProfile> getUsers() {
        return loginToProfile;
    }
    public static void setUsers(Map<String, UserProfile> profiles) {
        loginToProfile = profiles;
    }
    private static HashMap<String, UserProfile> initHashMap(){
        HashMap<String, UserProfile> hash = new HashMap<String, UserProfile>();
        hash.put("admin", new UserProfile("admin","qwerty","admin@bk.com"));
        hash.put("KarSich", new UserProfile("KarSich","qwerty","KarSich@bk.com"));
        hash.put("anon", new UserProfile("anon","anon","anon@bk.com"));
        return hash;
    }


}
