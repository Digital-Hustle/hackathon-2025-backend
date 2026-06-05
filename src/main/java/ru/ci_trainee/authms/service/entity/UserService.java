package ru.ci_trainee.authms.service.entity;

import ru.ci_trainee.authms.model.entity.User;

import java.util.UUID;

public interface UserService {

    User getUser(String email);

    User getUser(UUID id);

    User save(User user);

    User update(User user);

    void updateLastLogin(UUID userId);
}
