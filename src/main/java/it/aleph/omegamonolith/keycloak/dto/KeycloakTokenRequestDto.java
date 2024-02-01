package it.aleph.omegamonolith.keycloak.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakTokenRequestDto {

    private String grantType;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
}
