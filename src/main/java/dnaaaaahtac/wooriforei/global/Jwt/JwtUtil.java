package dnaaaaahtac.wooriforei.global.Jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import io.jsonwebtoken.*;
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
            return bearerToken.substring(7);
        }

        return null;
    }

    public boolean validationToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException exception) {
            throw new CustomException(ErrorCode.EXPIRED_JWT_TOKEN);
        } catch (MalformedJwtException | UnsupportedJwtException exception) {
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        } catch (IllegalArgumentException exception) {
            throw new CustomException(ErrorCode.UNSUPPORTED_JWT_TOKEN);
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String createToken(String username) {
        Date date = new Date();
        long TOKEN_TIME = 60 * 60 * 1000;

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createToken(String username, long validityInMillis) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMillis);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }
}
