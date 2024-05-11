package dnaaaaahtac.wooriforei.global.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${jwt.secret.key}")
    private String secretKey;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private Key key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public boolean validationToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) { // JWT와 관련된 전체 예외 catch
            log.error("JWT validation error", e);
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String createToken(String username) {
        Date now = new Date();
        return BEARER_PREFIX + Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(now.getTime() + 3600000)) // 1시간
                .setIssuedAt(now)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String createToken(String username, long validityInMillis) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityInMillis))
                .signWith(key, signatureAlgorithm)
                .compact();
    }
}
