package org.sszepiet.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.sszepiet.model.Video;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Component
@RefreshScope
public class ExternalSuggestionClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalSuggestionClient.class);
    private static final ParameterizedTypeReference<List<Video>> VIDEO_LIST = new ParameterizedTypeReference<List<Video>>() {
    };

    @Value("${suggestions.server.url}")
    private String suggestionsServerUrl;

    private final RestTemplate restTemplate;

    public ExternalSuggestionClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Video> getAgeSuggestions(LocalDate localDate) {
        log.info("Fetching age suggestions...");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(suggestionsServerUrl)
                .queryParam("dateOfBirth", localDate);
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, null, VIDEO_LIST)
                .getBody();
    }

    public Flux<Video> getAgeSuggestionsReactive(LocalDate localDate) {
        return Flux.defer(() -> Flux.fromIterable(getAgeSuggestions(localDate)));
    }

    public List<Video> getLocaleSuggestions(Locale locale) {
        log.info("Fetching locale suggestions...");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(suggestionsServerUrl)
                .queryParam("locale", locale);
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, null, VIDEO_LIST)
                .getBody();
    }

    public Flux<Video> getLocaleSuggestionsReactive(Locale locale) {
        return Flux.defer(() -> Flux.fromIterable(getLocaleSuggestions(locale)));
    }

    public List<Video> getPreviouslyWatchedSuggestions() {
        log.info("Fetching previously watched suggestions...");
        return restTemplate.exchange(suggestionsServerUrl, HttpMethod.GET, null, VIDEO_LIST)
                .getBody();
    }

    public Flux<Video> getPreviouslyWatchedSuggestionsReactive() {
        return Flux.defer(() -> Flux.fromIterable(getPreviouslyWatchedSuggestions()));
    }
}
