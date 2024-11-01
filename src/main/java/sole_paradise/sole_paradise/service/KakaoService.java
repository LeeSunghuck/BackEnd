package sole_paradise.sole_paradise.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.model.KakaoTokenResponseDto;
import sole_paradise.sole_paradise.model.KakaoUserInfoResponseDto;
import sole_paradise.sole_paradise.repos.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private final UserRepository userRepository;

    @Value("${kakao.client_id}")
    private String clientId;

    private static final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private static final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";

    // 액세스 토큰 발급 메소드
    public Mono<KakaoTokenResponseDto> getAccessTokenFromKakao(String code) {
        return WebClient.create(KAUTH_TOKEN_URL_HOST)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .build())
                .retrieve()
                .bodyToMono(KakaoTokenResponseDto.class)
                .doOnNext(token -> log.info("Access Token: {}", token.getAccessToken()));
    }

    // 사용자 정보 가져오기
    public Mono<KakaoUserInfoResponseDto> getUserInfo(String accessToken) {
        return WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri("/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .doOnNext(userInfo -> log.info("Retrieved user info for ID: {}", userInfo.getId()));
    }

    @Transactional
    public Mono<Integer> saveUserInfo(KakaoUserInfoResponseDto userInfo, String refreshToken) {
        String email = userInfo.getKakaoAccount().getEmail();
        String profileNickname = userInfo.getKakaoAccount().getProfile().getNickname();

        if (email == null) {
            log.error("Email is missing. Cannot save user.");
            return Mono.empty();
        }

        // 기존 사용자 찾기 및 업데이트 또는 새 사용자 생성 후 저장
        return Mono.justOrEmpty(userRepository.findByAccountEmail(email))
                .flatMap(existingUser -> {
                    existingUser.setProfileNickname(profileNickname);
                    existingUser.setRefreshToken(refreshToken);
                    return Mono.just(userRepository.save(existingUser).getUserId()); // 기존 사용자 업데이트 후 ID 반환
                })
                .switchIfEmpty(Mono.defer(() -> {
                    User newUser = new User();
                    newUser.setAccountEmail(email);
                    newUser.setProfileNickname(profileNickname);
                    newUser.setRefreshToken(refreshToken);
                    return Mono.just(userRepository.save(newUser).getUserId()); // 새 사용자 생성 후 ID 반환
                }));
    }


    // 액세스 토큰 유효성 검증 메소드
    public Mono<Boolean> validateAccessToken(String accessToken) {
        return WebClient.create()
                .get()
                .uri("https://kapi.kakao.com/v1/user/access_token_info")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    log.warn("Invalid token: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Invalid access token"));
                })
                .bodyToMono(Void.class)
                .map(response -> true)
                .onErrorReturn(false); // 유효하지 않을 경우 false 반환
    }

    // 리프레시 토큰을 이용한 새 액세스 토큰 발급
    public Mono<String> refreshAccessToken(String refreshToken) {
        return WebClient.create(KAUTH_TOKEN_URL_HOST)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "refresh_token")
                        .queryParam("client_id", clientId)
                        .queryParam("refresh_token", refreshToken)
                        .build())
                .retrieve()
                .bodyToMono(KakaoTokenResponseDto.class)
                .map(KakaoTokenResponseDto::getAccessToken)
                .doOnNext(newAccessToken -> log.info("New Access Token: {}", newAccessToken));
    }
}