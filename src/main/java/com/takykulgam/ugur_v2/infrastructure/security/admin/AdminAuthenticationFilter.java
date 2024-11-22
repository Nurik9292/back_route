package com.takykulgam.ugur_v2.infrastructure.security.admin;


import com.takykulgam.ugur_v2.infrastructure.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AdminAuthenticationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtTokenProvider jwtTokenProvider;
    private final StaffDetailsService staffDetailsService;

    @Autowired
    public AdminAuthenticationFilter(HandlerExceptionResolver handlerExceptionResolver,
                                     JwtTokenProvider jwtTokenProvider,
                                     StaffDetailsService staffDetailsService) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtTokenProvider = jwtTokenProvider;
        this.staffDetailsService = staffDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {

            String token = authHeader.substring(7);
            String username = jwtTokenProvider.extractClaim(token, Claims::getSubject);
            StaffDetails staffDetails = staffDetailsService.loadUserByUsername(username);

            if(jwtTokenProvider.isTokenValid(username, staffDetails) &&
                    staffDetails.getStaffEntity().getStaffSession().getToken().equals(token)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(staffDetails, null, staffDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else throw new SecurityException("Session invalid. Please login again.");

            filterChain.doFilter(request, response);

        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }

    }
}
