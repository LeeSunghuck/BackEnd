package sole_paradise.sole_paradise.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
