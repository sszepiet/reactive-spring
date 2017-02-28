package org.sszepiet.service;

import org.springframework.stereotype.Service;
import org.sszepiet.entity.SimpleEntity;
import org.sszepiet.repository.SimpleEntityRepository;
import org.sszepiet.util.Sleeper;

import java.time.Duration;

@Service
public class RepositoryService {

    private final SimpleEntityRepository repository;

    public RepositoryService(SimpleEntityRepository repository) {
        this.repository = repository;
    }

    public void save(SimpleEntity simpleEntity) {
        Sleeper.sleep(Duration.ofMillis(200L));
        repository.save(simpleEntity);
    }

    public SimpleEntity findOne(long id) {
        return repository.findOne(id);
    }
}
