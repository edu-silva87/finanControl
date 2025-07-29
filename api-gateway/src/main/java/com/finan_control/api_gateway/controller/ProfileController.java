package com.finan_control.api_gateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/userinfo")
    public ResponseEntity<Map<String, Object>> userInfo(
            @AuthenticationPrincipal OidcUser oidcUser,
            @RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {

        Map<String, Object> attributesMap = new HashMap<>(oidcUser.getAttributes());
        attributesMap.put("token", oidcUser.getIdToken().getTokenValue());
        attributesMap.put("clientName", authorizedClient.getClientRegistration().getClientId());
        attributesMap.put("userAttributes", oidcUser.getAttributes());
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attributesMap);
    }
}

