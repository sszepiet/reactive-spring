package org.sszepiet.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.sszepiet.model.Video;
import reactor.core.publisher.Flux;

import java.net.URI;
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
    private final WebClient webClient;

    public ExternalSuggestionClient(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    public List<Video> getAgeSuggestions(LocalDate localDate) {
        log.info("Fetching age suggestions...");
        return restTemplate.exchange(buildUriWithQueryParam("dateOfBirth", localDate), HttpMethod.GET, null, VIDEO_LIST)
                .getBody();
    }

    public Flux<Video> getAgeSuggestionsReactive(LocalDate localDate) {
        return webClient.get()
                .uri(buildUriWithQueryParam("dateOfBirth", localDate))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToFlux(Video.class));
    }

    public List<Video> getLocaleSuggestions(Locale locale) {
        log.info("Fetching locale suggestions...");
        return restTemplate.exchange(buildUriWithQueryParam("locale", locale), HttpMethod.GET, null, VIDEO_LIST)
                .getBody();
    }

    public Flux<Video> getLocaleSuggestionsReactive(Locale locale) {
        return webClient.get()
                .uri(buildUriWithQueryParam("locale", locale))
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToFlux(Video.class));
    }

    public List<Video> getPreviouslyWatchedSuggestions() {
        log.info("Fetching previously watched suggestions...");
        return restTemplate.exchange(suggestionsServerUrl, HttpMethod.GET, null, VIDEO_LIST)
                .getBody();
    }

    public Flux<Video> getPreviouslyWatchedSuggestionsReactive() {
        return webClient.get()
                .uri(suggestionsServerUrl)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToFlux(Video.class));
    }

    private URI buildUriWithQueryParam(String queryParamName, Object queryParam) {
        return UriComponentsBuilder.fromHttpUrl(suggestionsServerUrl)
                .queryParam(queryParamName, queryParam).build().encode().toUri();
    }

}
