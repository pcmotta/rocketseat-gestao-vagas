package br.com.attomtech.gestaovagas.security;

import br.com.attomtech.gestaovagas.providers.JWTCandidateProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Component
public class SecurityCandidateFilter extends OncePerRequestFilter {

    @Autowired
    JWTCandidateProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().startsWith("/candidate");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        SecurityContextHolder.getContext().setAuthentication(null);
        var token = request.getHeader("Authorization");

        if (Objects.nonNull(token)) {
            var decodedToken = jwtProvider.validateToken(token);

            if (Objects.isNull(decodedToken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            request.setAttribute("candidate_id", decodedToken.getSubject());

            var roles = decodedToken.getClaim("roles").asList(Object.class);
            var grants = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
                .toList();

            var auth = new UsernamePasswordAuthenticationToken(decodedToken.getSubject(), null, grants);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
