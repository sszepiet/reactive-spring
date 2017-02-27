package org.sszepiet.controller;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.sszepiet.model.Video;
import reactor.core.publisher.Flux;

public class WebClientConsumptionTest {

    @Test
    public void showWebClientConsumption() {
        Flux<Video> videoFlux = WebClient.create().get().uri("http://localhost:8081/suggestions/flux/test/non-browser")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToFlux(Video.class));

        videoFlux.collectList().block().forEach(video -> System.out.println(video.toString()));
    }
}
