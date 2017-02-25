package org.sszepiet.controller;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.sszepiet.model.Genre;
import org.sszepiet.model.Video;
import org.sszepiet.util.Sleeper;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

/**
 * Simulates external controller.
 */
@RestController
@RequestMapping("/external/suggestions")
public class SuggestionController {

    private static final Logger log = LoggerFactory.getLogger(SuggestionController.class);

    @GetMapping
    public List<Video> getSuggestions(@RequestParam(value = "dateOfBirth", required = false)
                                      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate,
                                      @RequestParam(value = "locale", required = false) Locale locale) {
        if (localDate != null) {
            return getAgeSuggestions(localDate);
        } else if (locale != null) {
            return getLocaleSuggestions(locale);
        } else {
            return getPreviouslyWatchedSuggestions();
        }
    }

    private List<Video> getAgeSuggestions(LocalDate localDate) {
        log.info("Providing age suggestions...");
        Sleeper.sleep(Duration.ofMillis(500L));
        return Lists.newArrayList(Video.builder()
                .title("Power rangers")
                .genre(Genre.SCIENCE_FICTION)
                .publishYear(2017)
                .build());
    }

    private List<Video> getLocaleSuggestions(Locale locale) {
        log.info("Providing locale suggestions...");
        Sleeper.sleep(Duration.ofMillis(500L));
        return Lists.newArrayList(Video.builder()
                .title("Sami swoi")
                .genre(Genre.COMEDY)
                .publishYear(1967)
                .build());
    }

    private List<Video> getPreviouslyWatchedSuggestions() {
        log.info("Providing previously watched suggestions...");
        Sleeper.sleep(Duration.ofMillis(500L));
        return Lists.newArrayList(
                Video.builder()
                        .title("Sami swoi")
                        .genre(Genre.COMEDY)
                        .publishYear(1967)
                        .build(),
                Video.builder()
                        .title("The blues brothers")
                        .genre(Genre.COMEDY)
                        .publishYear(1980)
                        .build()
        );
    }
}
