package fr.carrefour.delivery.configurations;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import fr.carrefour.delivery.enums.Permission;
import fr.carrefour.delivery.enums.Role;
import fr.carrefour.delivery.utils.Constants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {Constants.AUTH_ENDPOINT+"/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers(Constants.CLIENT_ENDPOINT+"/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(GET, Constants.CLIENT_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.USER_READ.name())
                                .requestMatchers(POST, Constants.CLIENT_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.USER_CREATE.name())
                                .requestMatchers(PUT, Constants.CLIENT_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_UPDATE.name(), Permission.USER_UPDATE.name())
                                .requestMatchers(DELETE,Constants.CLIENT_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_DELETE.name(), Permission.USER_DELETE.name())
                                .requestMatchers(Constants.LIVRAISON_ENDPOINT+"/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(GET, Constants.LIVRAISON_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.USER_READ.name())
                                .requestMatchers(POST, Constants.LIVRAISON_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.USER_CREATE.name())
                                .requestMatchers(PUT, Constants.LIVRAISON_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_UPDATE.name(), Permission.USER_UPDATE.name())
                                .requestMatchers(DELETE, Constants.LIVRAISON_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_DELETE.name(), Permission.USER_DELETE.name())
                                .requestMatchers(Constants.KAFKA_ENDPOINT+"/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                                .requestMatchers(GET, Constants.KAFKA_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_READ.name(), Permission.USER_READ.name())
                                .requestMatchers(POST, Constants.KAFKA_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_CREATE.name(), Permission.USER_CREATE.name())
                                .requestMatchers(PUT, Constants.KAFKA_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_UPDATE.name(), Permission.USER_UPDATE.name())
                                .requestMatchers(DELETE, Constants.KAFKA_ENDPOINT+"/**").hasAnyAuthority(Permission.ADMIN_DELETE.name(), Permission.USER_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl(Constants.AUTH_ENDPOINT+"/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }
}
