package sole_paradise.sole_paradise.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // 역직렬화를 위한 기본 생성자
@JsonIgnoreProperties(ignoreUnknown = true)  // 응답에 존재하지 않는 필드는 무시
public class KakaoTokenResponseDto {

    @JsonProperty("token_type")
    private String tokenType;  // 토큰 타입 (Bearer 등)

    @JsonProperty("access_token")
    private String accessToken;  // 액세스 토큰

    @JsonProperty("refresh_token")
    private String refreshToken;  // 리프레시 토큰

    @JsonProperty("expires_in")
    private Integer expiresIn;  // 액세스 토큰의 만료 시간 (초 단위)

    @JsonProperty("refresh_token_expires_in")
    private Integer refreshTokenExpiresIn;  // 리프레시 토큰의 만료 시간 (초 단위)

    @JsonProperty("scope")
    private String scope;  // 권한 범위 (예: profile, account)
}
