package dnaaaaahtac.wooriforei.global.config;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class CustomCorsFilter extends OncePerRequestFilter {

    private static final List<String> allowedOrigins = Arrays.asList(
            "https://www.wooriforei.info",
            "https://cat.wooriforei.info",
            "http://localhost:3000");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestOrigin = request.getHeader("Origin");

        if (requestOrigin == null || allowedOrigins.contains(requestOrigin)) {
            response.setHeader("Access-Control-Allow-Origin", (requestOrigin != null) ? requestOrigin : "*");
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

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                filterChain.doFilter(request, response);
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
