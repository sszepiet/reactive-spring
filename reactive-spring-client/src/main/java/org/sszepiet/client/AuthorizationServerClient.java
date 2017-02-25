package org.sszepiet.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.sszepiet.model.User;

@Component
public class AuthorizationServerClient {

    private static final Logger log = LoggerFactory.getLogger(AuthorizationServerClient.class);

    @Value("${auth.server.url}")
    private String authorizationServerUrl;

    private final RestTemplate restTemplate;

    public AuthorizationServerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserData(long userId) {
        log.info("Fetching user...");
        return restTemplate.getForObject(authorizationServerUrl + userId, User.class);
    }
}
