package sole_paradise.sole_paradise;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // 프론트엔드 도메인 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 허용할 헤더
                .exposedHeaders("Authorization", "Content-Type") // 노출할 헤더
                .allowCredentials(true) // 자격 증명 허용
                .maxAge(3600); // 캐시 시간 설정 (초 단위)
    }
}
