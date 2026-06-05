package ru.ci_trainee.authms.service.entity.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ci_trainee.authms.exception.exception.EntityAlreadyExistsException;
import ru.ci_trainee.authms.model.entity.User;
import ru.ci_trainee.authms.repository.UserRepository;
import ru.ci_trainee.authms.service.entity.UserService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityAlreadyExistsException("User with such email already exists");
        }

        return userRepository.save(user.toBuilder()
                .id(UUID.randomUUID())
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build());
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateLastLogin(UUID userId) {
        var user = getUser(userId);
        userRepository.save(user.toBuilder()
                .lastLogin(LocalDateTime.now())
                .build());
    }
}
