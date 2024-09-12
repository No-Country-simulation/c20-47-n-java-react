package com.FlowBanck.security.filter;

import com.FlowBanck.exception.login.TokenExpiredException;
import com.FlowBanck.payload.ErrorResponse;
import com.FlowBanck.security.jwt.JwtUtils;
import com.FlowBanck.service.UserDetailServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);

            try {
                if (!jwtUtils.isTokenValid(token)) {
                    throw new TokenExpiredException("El token a expirado o no es válido");
                }
                String username = this.jwtUtils.getUserFromToken(token);
                UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (TokenExpiredException exception){
                // FALTA MEJORAR POR AHORA LO DEJO ASI !
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .timestamp(LocalDateTime.now().toString())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .build();
                response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                return;
            }

        }
        filterChain.doFilter(request,response);
    }
}
