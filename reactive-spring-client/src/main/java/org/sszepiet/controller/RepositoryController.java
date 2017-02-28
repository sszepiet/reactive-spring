package org.sszepiet.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.sszepiet.entity.SimpleEntity;
import org.sszepiet.service.RepositoryService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/repositoryobjects")
public class RepositoryController {

    private final RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @PostMapping
    public ResponseEntity saveRepositoryObject(@RequestBody SimpleEntity repositoryObject) {
        repositoryService.save(repositoryObject);
        return ResponseEntity.created(UriComponentsBuilder
                .fromUriString("http://localhost:8081/repositoryobjects/" + repositoryObject.getId()).build().encode().toUri())
                .build();
    }

    @GetMapping("/{id}")
    public Mono<SimpleEntity> getRepositoryObject(@PathVariable long id) {
        return Mono.just(repositoryService.findOne(id));
    }
}
