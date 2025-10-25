package ru.core.profilems.dto;

public class CurrentUser {
    private static final ThreadLocal<String> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> username = new ThreadLocal<>();

    public void setUserId(String id) {
        userId.set(id);
    }

    public String getUserId() {
        return userId.get();
    }

    public void setUsername(String name) {
        username.set(name);
    }

    public String getUsername() {
        return username.get();
    }

    public void clear() {
        userId.remove();
        username.remove();
    }
}
