package com.church.appChurch.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // --- ÁREA PÚBLICA (VISITANTES) ---
                        // Autenticação
                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()

                        //webhooks
                        .requestMatchers(HttpMethod.POST, "/api/webhooks/**").permitAll()

                        // Eventos: Qualquer um pode VER e se INSCREVER
                        .requestMatchers(HttpMethod.GET, "/api/igreja/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/igrejas/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/eventos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/evento/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/igrejas/*/eventos/*/inscricao").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/igrejas/*/eventos/*/checkout").permitAll()

                        // Ministérios: Necessário para carregar filtros públicos
                        .requestMatchers(HttpMethod.GET, "/api/ministerios/**").permitAll()

                        // Swagger (Opcional)
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                        // --- ÁREA RESTRITA (ADMIN/MEMBROS) ---
                        // Qualquer alteração (Criar, Editar, Excluir) exige login
                        .requestMatchers(HttpMethod.POST, "/api/igrejas/*/eventos/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/igrejas/*/eventos/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/igrejas/*/eventos/**").authenticated()

                        // Gestão de Membros é totalmente restrita
                        .requestMatchers("/api/igrejas/*/membros/**").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Permite o frontend (ajuste se necessário, "*" libera tudo para desenvolvimento)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:3000", "http://localhost:8080", "https://ecclesia-manager-ten.vercel.app"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
