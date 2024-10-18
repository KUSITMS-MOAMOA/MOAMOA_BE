package corecord.dev.domain.token.constant;

import corecord.dev.common.base.BaseSuccessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenSuccessStatus implements BaseSuccessStatus {
    REISSUE_ACCESS_TOKEN_SUCCESS(HttpStatus.CREATED, "S001", "Access Token 재발급 성공입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}