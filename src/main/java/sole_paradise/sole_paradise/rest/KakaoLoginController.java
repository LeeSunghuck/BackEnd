package sole_paradise.sole_paradise.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import sole_paradise.sole_paradise.security.JwtTokenProvider;
import sole_paradise.sole_paradise.service.KakaoService;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/test")
    public Mono<ResponseEntity<Object>> test(@RequestParam("code") String code) {
        log.info("Received Authorization Code: {}", code);

        return kakaoService.getAccessTokenFromKakao(code)
                .flatMap(response -> {
                    String accessToken = response.getAccessToken();
                    String refreshToken = response.getRefreshToken();

                    log.info("Access Token: {}", accessToken);
                    log.info("Refresh Token: {}", refreshToken);

                    return kakaoService.getUserInfo(accessToken)
                            .flatMap(userInfo -> 
                                kakaoService.saveUserInfo(userInfo, refreshToken)
                                    .flatMap(userId -> {
                                        log.info("User information saved for user: {}", userId);

                                        // 자체 JWT 발행
                                        String jwtToken = jwtTokenProvider.generateToken(userId.toString());
                                        log.info("Generated JWT Token: {}", jwtToken);

                                        String redirectUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/petRegister")
                                                .queryParam("jwtToken", jwtToken)
                                                .queryParam("accessToken", accessToken)
                                                .queryParam("refreshToken", refreshToken)
                                                .queryParam("userId", userId) // SQL에서 가져온 userId 사용
                                                .encode()  // 인코딩 처리
                                                .toUriString();

                                        log.info("Redirecting to URL with JWT Token: {}", redirectUrl);

                                        return Mono.just(ResponseEntity.status(HttpStatus.SEE_OTHER)
                                                .location(URI.create(redirectUrl))
                                                .build());
                                    })
                            );
                })
                .onErrorResume(e -> {
                    log.error("로그인 중 오류 발생", e);
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "로그인 실패");

                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(errorResponse));
                });
    }
}