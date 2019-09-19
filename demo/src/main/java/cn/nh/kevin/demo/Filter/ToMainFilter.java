package cn.nh.kevin.demo.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 标题: 能否自动登录过滤器
 * 描述:
 * 版权: Kevin
 * 作者: xck
 * 时间: 2019-09-03 19:13
 */
@WebFilter(urlPatterns = "/admin/main", filterName = "ToMainFilter")
public class ToMainFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToMainFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        LOGGER.info("session is {}", request.getSession().getId());
        if (request.getSession().getAttribute("user") == null) {
            LOGGER.info("tologin");
            String url = request.getRequestURI();
            request.getRequestDispatcher("/admin/tologin").forward(request, response);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
