package corecord.dev.domain.token.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 604800000)
@AllArgsConstructor
@Builder
public class RefreshToken {
    @Id
    private String refreshToken;
    private Long userId;
}