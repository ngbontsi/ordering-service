package com.example.ordering.filter;

import com.example.ordering.tenant.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TenantFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try {
            String tenant = req.getHeader("X-Client-ID");
            if (tenant == null || tenant.isBlank()) {
                res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                res.getWriter().write("Missing X-Client-ID header");
                return;
            }
            TenantContext.setCurrentTenant(tenant);
            chain.doFilter(req, res);
        } finally {
            TenantContext.clear();
        }
    }
}
