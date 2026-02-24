package ru.core.profilems.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.core.profilems.exception.exception.AccessDeniedException;
import ru.core.profilems.repository.ProfileRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityService {

    private final ProfileRepository profileRepository;

    public UUID getCurrentUserId() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof UserAuthentication ua)) {
            throw new AccessDeniedException("No authentication found");
        }
        return ua.getUserId();
    }

    public String getCurrentUserRole() {
        UserAuthentication auth = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getRole() : null;
    }

    public boolean isProfileOwner(UUID profileId) {
        try {
            UUID currentUserId = getCurrentUserId();
            return currentUserId != null && currentUserId.equals(profileId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAdmin() {
        UserAuthentication auth = (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return auth != null && "ADMIN".equals(auth.getRole());
    }

    // Универсальный метод для проверки доступа
    public boolean canAccessProfile(UUID profileId) {
        return isProfileOwner(profileId) || isAdmin();
    }

    // Проверка наличия любой из ролей
    public boolean hasAnyRole(String... roles) {
        String currentRole = getCurrentUserRole();
        if (currentRole == null) return false;

        for (String role : roles) {
            if (currentRole.equals(role)) return true;
        }
        return false;
    }
}
