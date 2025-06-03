package com.technova.shopverse.shopverse_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // acceso libre a H2
                        .requestMatchers("/api/products/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/categories/**").hasAnyRole("USER", "ADMIN")
                        //.requestMatchers(HttpMethod.GET,"/api/products/**").hasAuthority("USER")
                        .anyRequest().authenticated()
                        //.anyRequest().denyAll()
                        //Deniega todo lo que no esta especificado arriba
                )
                //permite los FrameOptions - consumo de API de tercero, como la BD
                .headers( httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(
                                        (request, response, authException) ->
                                        response.sendError(403)
                                )
                )
                /*.formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer
                                .loginPage("/index.html")
                                .loginProcessingUrl("/api/login")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .successHandler(
                                        (request, response, authentication) ->
                                          clearAuthenticationAtributtes(request)
                                )
                )*/
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // Despues de declarar un Security, se debe declarar un Authentication

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password("{noop}admin123") // {noop} desactiva el cifrado para pruebas
                .roles("ADMIN")
                .build();

        UserDetails user = User
                .withUsername("user")
                .password("{noop}user123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}