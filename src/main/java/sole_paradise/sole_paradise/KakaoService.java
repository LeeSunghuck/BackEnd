package sole_paradise.sole_paradise;

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

    public Mono<String> getAccessTokenFromKakao(String code) {
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
                .map(KakaoTokenResponseDto::getAccessToken)
                .doOnNext(accessToken -> log.info("Access Token: {}", accessToken));
    }

    public Mono<KakaoUserInfoResponseDto> getUserInfo(String accessToken) {
        log.info("Using Access Token: {}", accessToken);

        return WebClient.create(KAUTH_USER_URL_HOST)
                .get()
                .uri("/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    log.error("4xx Error - Unauthorized or Invalid Request: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Client Error"));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    log.error("5xx Error - Server Issue: {}", response.statusCode());
                    return Mono.error(new RuntimeException("Server Error"));
                })
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .doOnNext(this::saveUserInfo)
                .doOnError(e -> log.error("Error retrieving user info", e));
    }

    @Transactional
    public void saveUserInfo(KakaoUserInfoResponseDto userInfo) {
        String email = userInfo.getKakaoAccount().getEmail();
        String profileNickname = userInfo.getKakaoAccount().getProfile().getNickname();
        String nickname = null;  // 별명은 수동 입력할 수 있도록 null로 처리
        String address = "";  // 주소 기본값은 빈 문자열로 설정

        if (email == null) {
            log.error("User email is missing, cannot save user information.");
            return;
        }

        User existingUser = userRepository.findByAccountEmail(email);

        if (existingUser != null) {
            existingUser.setProfileNickname(profileNickname);  // 프로필 닉네임 업데이트
            userRepository.save(existingUser);
            log.info("Updated existing user: {}", email);
        } else {
            User newUser = new User();
            newUser.setAccountEmail(email);
            newUser.setProfileNickname(profileNickname);
            newUser.setNickname(nickname);  // 별명은 사용자가 나중에 설정
            newUser.setAddress(address);  // 주소 기본값을 빈 문자열로 설정
            userRepository.save(newUser);
            log.info("Saved new user: {}", email);
        }
    }
}
