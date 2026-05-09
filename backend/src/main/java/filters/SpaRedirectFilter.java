package filters;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SpaRedirectFilter implements Filter{
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length());

        // Si es API, OpenAPI o archivo estático, seguir normal
        if (path.startsWith("/rest/") ||
            path.startsWith("/openapi/") ||
            path.contains(".") ||
            path.contains("/swagger-ui") 
        ) {
            chain.doFilter(request, response);
            return;
        }

        // Redirigir a index.html
        req.getRequestDispatcher("/index.html").forward(req, res);
    }
}
