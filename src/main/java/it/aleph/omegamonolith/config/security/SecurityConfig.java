package it.aleph.omegamonolith.config.security;

import it.aleph.omegamonolith.keycloak.JwtAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;
    private final static String ROLE_ADMIN_LIBRARY = "ROLE_ADMIN_LIBRARY";
    private final static String ROLE_USER_LIBRARY = "ROLE_USER_LIBRARY";
    private final static String ROLE_ADMIN_LOAN = "ROLE_ADMIN_LOAN";
    private final static String ROLE_USER_LOAN = "ROLE_USER_LOAN";
    private final static String ROLE_ADMIN_JOB = "ROLE_ADMIN_JOB";

    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity security) throws Exception {
        return security.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(HttpMethod.GET, "/book/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY, ROLE_USER_LIBRARY)
                        .requestMatchers(HttpMethod.DELETE, "/book/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.PATCH, "/book/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.POST, "/book").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.PUT, "/book/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.GET, "/books").hasAnyAuthority(ROLE_USER_LIBRARY, ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.POST, "/books").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.PATCH, "/book/associate/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY)

                        .requestMatchers(HttpMethod.POST, "/author").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.DELETE, "/author/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.PUT, "/author/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.GET, "/author/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY, ROLE_USER_LIBRARY)
                        .requestMatchers(HttpMethod.GET, "/authors").hasAnyAuthority(ROLE_USER_LIBRARY, ROLE_ADMIN_LIBRARY)

                        .requestMatchers(HttpMethod.POST, "/tag").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.DELETE, "/tag/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.PUT, "/tag/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY)
                        .requestMatchers(HttpMethod.GET, "/tag/*").hasAnyAuthority(ROLE_ADMIN_LIBRARY, ROLE_USER_LIBRARY)
                        .requestMatchers(HttpMethod.GET, "/tags").hasAnyAuthority(ROLE_USER_LIBRARY, ROLE_ADMIN_LIBRARY)



                        .requestMatchers(HttpMethod.GET, "/loan/*").hasAnyAuthority(ROLE_ADMIN_LOAN)
                        .requestMatchers(HttpMethod.POST, "/loan").hasAnyAuthority(ROLE_ADMIN_LOAN, ROLE_USER_LOAN)
                        .requestMatchers(HttpMethod.DELETE, "/loan/*").hasAnyAuthority(ROLE_ADMIN_LOAN)
                        .requestMatchers(HttpMethod.PATCH, "/loan/*").hasAnyAuthority(ROLE_ADMIN_LOAN)

                        .requestMatchers(HttpMethod.GET, "/job").hasAnyAuthority(ROLE_ADMIN_JOB))

                .csrf(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter).decoder(JwtDecoders.fromIssuerLocation("http://localhost:8080/realms/OmegaLegacy"))))
                .build();

    }
}
