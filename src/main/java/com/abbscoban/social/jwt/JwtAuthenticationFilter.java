package com.abbscoban.social.jwt;

import java.io.IOException;
import java.nio.channels.Pipe.SourceChannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header;
        String token;
        String username;
        header=request.getHeader("Authorization");

        if(header==null) {
            filterChain.doFilter(request, response);
            return;
        }
        token=header.substring(7);

        try {
            username= jwtService.getUsernameByToken(token);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {

                UserDetails userDetails= userDetailsService.loadUserByUsername(username);
                if(userDetails!=null && jwtService.isTokenExpired(token)) {

                    UsernamePasswordAuthenticationToken authendication=
                            new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    authendication.setDetails(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authendication);
                }

            }


        } catch (ExpiredJwtException e) {
            System.out.println("Token süresi dolmuştur. "+ e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Genele bir hata oluştu "+ e.getMessage());
        }

        filterChain.doFilter(request, response);


    }

}

