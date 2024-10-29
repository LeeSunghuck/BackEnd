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
    public void saveUserInfo(KakaoUserInfoResponseDto userInfo, String refreshToken) {
        String email = userInfo.getKakaoAccount().getEmail();
        String profileNickname = userInfo.getKakaoAccount().getProfile().getNickname();

        if (email == null) {
            log.error("Email is missing. Cannot save user.");
            return;
        }

        User existingUser = userRepository.findByAccountEmail(email);
        if (existingUser != null) {
            existingUser.setProfileNickname(profileNickname);
            existingUser.setRefreshToken(refreshToken);
            userRepository.save(existingUser);
            log.info("Updated user: {}", email);
        } else {
            User newUser = new User();
            newUser.setAccountEmail(email);
            newUser.setProfileNickname(profileNickname);
            newUser.setRefreshToken(refreshToken);
            userRepository.save(newUser);
            log.info("Saved new user: {}", email);
        }
    }

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
