package com.demo.book.interceptor;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@WebFilter("/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // empty
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        long  start = System.nanoTime();
        try {
            chain.doFilter(req, resp);
        } finally {
            long finish = System.nanoTime();;
            long time = finish - start;
            LOGGER.trace("{}: {} ms ", ((HttpServletRequest) req).getRequestURI(),  time);
        }
    }

    @Override
    public void destroy() {
        // empty
    }
}

