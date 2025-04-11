package com.rentalservice.config;

import com.rentalservice.security.JwtCustomAuthenticationFilter;
import com.rentalservice.security.LoginSocialSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//resource server

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtCustomAuthenticationFilter jwtCustomAuthenticationFilter,
            LoginSocialSuccessHandler successHandler) throws Exception {
        http

                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // Permite todas as requisições
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF apenas para testes
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())) // Mantém CORS permitido
                .formLogin(Customizer.withDefaults()) // Habilita login formulário
                .httpBasic(Customizer.withDefaults()); // Permite autenticação básica

        return http.build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults(){
        return new GrantedAuthorityDefaults("");
    }

    // CONFIGURA, NO TOKEN JWT, O PREFIXO SCOPE
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(
                "/v2/api-docs/**",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**",
                "/actuator/**"
        );
    }




}
