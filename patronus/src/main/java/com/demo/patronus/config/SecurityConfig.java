//package com.demo.patronus.config;
//
//
//import com.demo.patronus.security.JwtConverter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtDecoders;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//OAuth2ResourceServerConfigurer
//import java.io.IOException;
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    public static final String ADMIN = "ADMIN";
//    public static final String USER = "USER";
//    private final JwtConverter jwtConverter;
//
//
//        @Bean
//        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//            return http
//                    .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
//                            .requestMatchers(HttpMethod.GET, "/api/streams", "/api/streams/**").permitAll()
//                            .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
//                            .requestMatchers("/swagger-ui.html",
//                                    "/swagger-ui/**",
//                                    "/v3/api-docs",
//                                    "/v3/api-docs/**").permitAll()
//                            .anyRequest().authenticated())
//                    .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(
//                            jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
//                    .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                    .cors(Customizer.withDefaults())
//                    .build();
//        }
//
//}
