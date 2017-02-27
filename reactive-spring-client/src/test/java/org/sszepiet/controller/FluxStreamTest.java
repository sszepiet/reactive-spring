package org.sszepiet.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.sszepiet.client.AuthorizationServerClient;
import org.sszepiet.client.ExternalSuggestionClient;
import org.sszepiet.model.Video;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.mockito.Mockito.mock;

public class FluxStreamTest {

    private WebTestClient webTestClient;

    @Before
    public void prepare() {
        webTestClient = WebTestClient
                .bindToController(new AggregatingSuggestionController(mock(AuthorizationServerClient.class), mock(ExternalSuggestionClient.class)))
                .build();
    }

    @Test
    public void webClientTest() {
        FluxExchangeResult<Video> objectFluxExchangeResult = webTestClient
                .get()
                .uri("/suggestions/flux/test/non-browser")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Video.class)
                .returnResult();

        StepVerifier.create(objectFluxExchangeResult.getResponseBody())
                .expectNext(Video.builder().title("Sami swoi").build(), Video.builder().title("Power rangers").build())
                .verifyComplete();
    }

    @Test
    public void virtualTimeTest() {
        StepVerifier.withVirtualTime(() -> Mono.just("Szymon").delayElement(Duration.ofSeconds(30L)))
                .thenAwait(Duration.ofSeconds(30L))
                .expectNext("Szymon")
                .verifyComplete();
    }

    @Test
    public void withoutVirtualTimeTest() {
        Mono<String> justSzymon = Mono.just("Szymon").delayElement(Duration.ofSeconds(4L));
        StepVerifier.create(justSzymon)
                .expectNext("Szymon")
                .verifyComplete();
    }
}