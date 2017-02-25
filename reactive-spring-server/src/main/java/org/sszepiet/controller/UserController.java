package org.sszepiet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sszepiet.model.User;
import org.sszepiet.util.Sleeper;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

/**
 * Simulates external controller.
 */
@RestController
@RequestMapping("/external/authorization/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("{userId}")
    public User getUser(@PathVariable("userId") long userId) {
        log.info("Providing user...");
        Sleeper.sleep(Duration.ofMillis(500L));
        return User.builder()
                .id(userId)
                .name("Szymon Szepietowski")
                .dateOfBirth(LocalDate.of(1990, Month.JANUARY, 27))
                .locale(new Locale("pl", "pl"))
                .build();
    }
}
