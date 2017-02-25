package org.sszepiet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sszepiet.client.AuthorizationServerClient;
import org.sszepiet.client.ExternalSuggestionClient;
import org.sszepiet.model.User;
import org.sszepiet.model.Video;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/suggestions")
public class AggregatingSuggestionController {

    private static final Logger log = LoggerFactory.getLogger(AggregatingSuggestionController.class);

    private final AuthorizationServerClient authorizationServerClient;
    private final ExternalSuggestionClient externalSuggestionClient;

    public AggregatingSuggestionController(AuthorizationServerClient authorizationServerClient, ExternalSuggestionClient externalSuggestionClient) {
        this.authorizationServerClient = authorizationServerClient;
        this.externalSuggestionClient = externalSuggestionClient;
    }

    @GetMapping
    public List<Video> provideSuggestions() {
        log.info("Starting to fetch suggestions...");
        User user = authorizationServerClient.getUserData(1L);
        log.info("Obtained user: " + user.toString());
        List<Video> ageSuggestions = externalSuggestionClient.getAgeSuggestions(user.getDateOfBirth());
        log.info("Obtained age suggestions!");
        List<Video> localeSuggestions = externalSuggestionClient.getLocaleSuggestions(user.getLocale());
        log.info("Obtained locale suggestions!");
        List<Video> previoslyWatchedSuggestions = externalSuggestionClient.getPreviouslyWatchedSuggestions();
        log.info("Obtained previously watched suggestions!");
        List<Video> suggestions = Stream.of(ageSuggestions.stream(), localeSuggestions.stream(), previoslyWatchedSuggestions.stream())
                .flatMap(Function.identity())
                .distinct()
                .collect(Collectors.toList());
        log.info("Combined results!");
        return suggestions;
    }
}