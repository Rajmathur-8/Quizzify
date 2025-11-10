//package com.raj.quiz_app_backend.config;
//
//import com.raj.quiz_app_backend.security.CustomOAuth2UserService;
//import com.raj.quiz_app_backend.security.JwtAuthenticationFilter;
//import com.raj.quiz_app_backend.security.OAuth2SuccessHandler;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    private final JwtAuthenticationFilter jwtFilter;
//    private final CustomOAuth2UserService oAuth2UserService;
//    private final OAuth2SuccessHandler successHandler;
//
//    public WebSecurityConfig(JwtAuthenticationFilter jwtFilter,
//                             CustomOAuth2UserService oAuth2UserService,
//                             OAuth2SuccessHandler successHandler) {
//        this.jwtFilter = jwtFilter;
//        this.oAuth2UserService = oAuth2UserService;
//        this.successHandler = successHandler;
//    }
//
//    // Password encoder for hashing passwords
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    // AuthenticationManager for login and JWT auth
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//
//    // Security configuration for endpoints, JWT, and OAuth2
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                // Disable CSRF for stateless APIs
//                .csrf(csrf -> csrf.disable())
//
//                // Endpoint access control
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(
//                                "/api/auth/**",
//                                "/swagger-ui/**",
//                                "/v3/api-docs/**",
//                                "/api-docs/**",
//                                "/swagger-resources/**",
//                                "/swagger-resources/**/**",
//                                "/configuration/**",
//                                "/error",
//                                "/ws/**"              // for WebSocket leaderboard
//                        ).permitAll()
//                        .requestMatchers(HttpMethod.GET, "/api/quizzes/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                // Stateless session (no cookies)
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//
//                // OAuth2 Login Setup (Google)
//                .oauth2Login(oauth -> oauth
//                        .userInfoEndpoint(user -> user.userService(oAuth2UserService))
//                        .successHandler(successHandler)
//                        .failureUrl("/api/auth/login?error=true")
//                )
//
//                // Add custom JWT filter before default username filter
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//}
