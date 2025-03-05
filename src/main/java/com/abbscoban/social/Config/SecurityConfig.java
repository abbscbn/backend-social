package com.abbscoban.social.Config;
import java.beans.BeanProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.abbscoban.social.jwt.AuthEntryPoint;
import com.abbscoban.social.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String AUTHENTICATE="/authenticate";
    public static final String REGISTER="/register";
    public static final String REFLESH_TOKEN="/refleshtoken";
    public static final String[] APP_PATHS= {
            "/posts/**",
            "/comments/**",
            "/like/**",
            "/api/**",
            "/images/**",
            "/users/**",
            "/uploads/**"
    };
   

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.cors().and().csrf().disable()
                .authorizeHttpRequests(request->
                        request.requestMatchers(AUTHENTICATE,REGISTER,REFLESH_TOKEN)
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,APP_PATHS).permitAll()
                                .anyRequest()
                                .authenticated())
                .exceptionHandling().authenticationEntryPoint(authEntryPoint).and()
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();

        
    }
}

