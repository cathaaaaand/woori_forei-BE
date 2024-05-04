package dnaaaaahtac.wooriforei.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dnaaaaahtac.wooriforei.global.Jwt.JwtAuthorizationFilter;
import dnaaaaahtac.wooriforei.global.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService, objectMapper);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "https://www.wooriforei.info"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Authorization"); // Authorization 헤더 노출

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/**", "/api/images/**", "/api/communities/**", "/api/comments/**")) // 특정 경로에서만 CSRF 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 정책을 Stateless로 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations())).permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/test-cors").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/faqs/**").permitAll()
                        .requestMatchers("/api/openAPI/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/aws").permitAll()
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )
                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 여기서 람다 표현식 제거하고 빈을 참조하도록 변경
        // .cors(cors -> cors.configurationSource(request -> { ... })) 부분을 삭제
        ;
        return http.build();
    }
}
