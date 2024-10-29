package sole_paradise.sole_paradise;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/test")
    public Mono<ResponseEntity<Object>> test(@RequestParam("code") String code) {
        return kakaoService.getAccessTokenFromKakao(code)
                .flatMap(response -> {
                    String accessToken = response.getAccessToken();
                    String refreshToken = response.getRefreshToken();

                    return kakaoService.getUserInfo(accessToken)
                        .flatMap(userInfo -> {
                            kakaoService.saveUserInfo(userInfo, refreshToken);
                            log.info("User information saved for user: {}", userInfo.getId());

                            return Mono.just(
                                    ResponseEntity.status(HttpStatus.FOUND)
                                            .header("Location", "http://localhost:5173/main")
                                            .build()
                            );
                        });
                })
                .onErrorResume(e -> {
                    log.error("Error during login", e);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }
}
