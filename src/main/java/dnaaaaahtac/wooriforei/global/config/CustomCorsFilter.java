package dnaaaaahtac.wooriforei.global.config;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomCorsFilter extends OncePerRequestFilter {

    private static final Set<String> ALLOWED_ORIGINS = new HashSet<>(Arrays.asList(
            "https://www.wooriforei.info",
            "https://cat.wooriforei.info",
            "http://localhost:3000"
    ));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String origin = request.getHeader("Origin");
        if (origin != null && ALLOWED_ORIGINS.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }
        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        }
        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
        }
        if (response.getHeader("Access-Control-Allow-Credentials") == null) {
            response.addHeader("Access-Control-Allow-Credentials", "true");
        }

        try {
            filterChain.doFilter(request, response);
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
}
