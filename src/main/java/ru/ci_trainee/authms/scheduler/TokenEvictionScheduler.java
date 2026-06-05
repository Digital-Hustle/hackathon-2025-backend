package ru.ci_trainee.authms.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.ci_trainee.authms.service.entity.PasswordResetTokenService;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenEvictionScheduler {

    private final PasswordResetTokenService passwordResetTokenService;

    @Scheduled(cron = "${app.domain.auth.token.eviction}")
    @SchedulerLock(
            name = "TokenEvictionScheduler_tokenEvictionTask",
            lockAtLeastFor = "${app.domain.auth.token.lock-at-most-for}",
            lockAtMostFor = "${app.domain.auth.token.lock-at-least-for}"
    )
    public void evictExpiredTokens() {
        log.info("delete all expired tokens");

        passwordResetTokenService.deleteAllExpiredTokens();
    }
}
