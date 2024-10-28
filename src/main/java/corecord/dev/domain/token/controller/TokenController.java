package corecord.dev.domain.token.controller;

import corecord.dev.common.response.ApiResponse;
import corecord.dev.domain.token.constant.TokenSuccessStatus;
import corecord.dev.domain.token.dto.response.TokenResponse;
import corecord.dev.domain.token.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @GetMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse.AccessTokenResponse>> reissueAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        TokenResponse.AccessTokenResponse accessTokenResponse = tokenService.reissueAccessToken(request, response);
        return ApiResponse.success(TokenSuccessStatus.REISSUE_ACCESS_TOKEN_SUCCESS, accessTokenResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TokenResponse.AccessTokenResponse>> issueToken(
            HttpServletResponse response,
            @RequestHeader("tmpToken") String tmpToken
    ) {
        TokenResponse.AccessTokenResponse accessTokenResponse = tokenService.issueTokens(response, tmpToken);
        return ApiResponse.success(TokenSuccessStatus.ISSUE_TOKENS_SUCCESS, accessTokenResponse);
    }
}
