package ru.ci_trainee.authms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ci_trainee.authms.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
