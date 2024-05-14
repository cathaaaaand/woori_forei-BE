package dnaaaaahtac.wooriforei.global.config;


import io.jsonwebtoken.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomCorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, java.io.IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");

        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
