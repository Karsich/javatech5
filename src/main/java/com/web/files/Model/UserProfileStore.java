package com.web.files.Model;

import com.web.files.Model.UserProfile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class UserProfileStore implements HttpSessionAttributeListener, HttpSessionListener {
    private static final String USER_PROFILES_FILE = "WEB-INF/user-profiles.json";
    private ServletContext context;

    public UserProfileStore(ServletContext context) {
        this.context = context;
        loadUserProfiles();
    }

    private void loadUserProfiles() {
        File userProfilesFile = new File(context.getRealPath(USER_PROFILES_FILE));
        if (userProfilesFile.exists()) {
            try {
                String json = new String(Files.readAllBytes(Paths.get(userProfilesFile.getPath())));
                Map<String, UserProfile> userProfiles = context.getConversationMap().get("userProfiles");
                if (userProfiles == null) {
                    userProfiles = new HashMap<>();
                }
                userProfiles.clear();
                userProfiles.putAll(UserProfile.fromJson(json));
            } catch (IOException e) {
                System.err.println("Failed to load user profiles: " + e.getMessage());
            }
        }
    }

    private void saveUserProfiles() {
        File userProfilesFile = new File(context.getRealPath(USER_PROFILES_FILE));
        try {
            Files.write(userProfilesFile.toPath(), UserProfile.toJson(context.getConversationMap().get("userProfiles")).getBytes());
        } catch (IOException e) {
            System.err.println("Failed to save user profiles: " + e.getMessage());
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if (event.getName().equals("login") && event.getValue() != null) {
            UserProfile userProfile = (UserProfile) event.getValue();
            Map<String, UserProfile> userProfiles = context.getConversationMap().get("userProfiles");
            userProfiles.put(userProfile.getLogin(), userProfile);
            saveUserProfiles();
        }
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {}

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().equals("login") && event.getValue() != null) {
            UserProfile userProfile = (UserProfile) event.getValue();
            Map<String, UserProfile> userProfiles = context.getConversationMap().get("userProfiles");
            userProfiles.remove(userProfile.getLogin());
            saveUserProfiles();
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {}

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {}
}