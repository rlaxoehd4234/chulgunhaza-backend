package com.example.chulgunhazabackend.config;

import com.example.chulgunhazabackend.security.filter.SessionCheckFilter;
import com.example.chulgunhazabackend.security.handler.LoginSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final SessionCheckFilter sessionCheckFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        // CORS 설정
        httpSecurity.cors(
                httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                        .configurationSource(corsConfigurationSource())
        );

        // CSRF 설정
        httpSecurity.csrf(
                httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()
        );

        // SESSION 설정
        httpSecurity.sessionManagement(
                httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.NEVER)

        );

        // 인증 처리
        httpSecurity.formLogin(
                httpSecurityFormLoginConfigurer -> {

                    // 로그인 URL
                    httpSecurityFormLoginConfigurer.loginPage("/v1/employee/login");

                    // 인증 성공 처리
                    httpSecurityFormLoginConfigurer.successHandler(authenticationSuccessHandler);

                    // 인증 실패 처리
                    httpSecurityFormLoginConfigurer.failureHandler(authenticationFailureHandler);
                }
        );

        // 로그아웃 처리
        httpSecurity.logout(
            httpSecurityLogoutConfigurer -> {
                httpSecurityLogoutConfigurer.logoutUrl("/v1/employee/logout");

                httpSecurityLogoutConfigurer.logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json; charset=UTF-8"); // 200 OK
                    response.getWriter().write("로그아웃 성공");
                    response.getWriter().flush();
                });
                httpSecurityLogoutConfigurer.invalidateHttpSession(true);
                httpSecurityLogoutConfigurer.deleteCookies("JSESSIONID");
            }
        );


        // 권한 처리
        httpSecurity.exceptionHandling(
                httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler)
        );


        // 필터 위치 지정 
        httpSecurity.addFilterBefore(sessionCheckFilter, UsernamePasswordAuthenticationFilter.class);

        // daoAuthenticationProvider 지정
        httpSecurity.authenticationProvider(daoAuthenticationProvider());


        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return  daoAuthenticationProvider;
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList("*")); //TODO: "http://localhost:3000 추가
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        corsConfiguration.setAllowCredentials(true); // 헤더 쿠키 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
