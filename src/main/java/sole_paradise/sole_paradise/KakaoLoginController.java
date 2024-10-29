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
                .flatMap(accessToken -> kakaoService.getUserInfo(accessToken)
                    .flatMap(userInfo -> {
                        // 사용자 정보 저장 및 로그 남기기
                        kakaoService.saveUserInfo(userInfo);
                        log.info("User information saved successfully for user: {}", userInfo.getId());

                        // 메인 페이지로 리다이렉트 (302 Found)
                        return Mono.just(
                            ResponseEntity.status(HttpStatus.FOUND)
                                .header("Location", "/main")  // 리다이렉트할 경로 설정
                                .build()
                        );
                    }))
                .onErrorResume(e -> {
                    // 에러 발생 시 로그 남기고 500 응답 반환
                    log.error("Failed to save user information", e);
                    return Mono.just(
                        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
                    );
                });
    }
}
