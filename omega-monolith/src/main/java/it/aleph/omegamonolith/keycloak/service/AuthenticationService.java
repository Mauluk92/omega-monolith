package it.aleph.omegamonolith.keycloak.service;

import it.aleph.omegamonolith.keycloak.dto.KeycloakTokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthenticationService {




    public Optional<KeycloakTokenResponseDto> getToken(String username, String password){
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("grant_type", "password");
        requestParams.add("client_id", "login-app");
        requestParams.add("client_secret", "6dvPH7vKb7g0rkTmjsALukcg635RJT1k");
        requestParams.add("username", username);
        requestParams.add("password", password);


        return null;
    }
}
