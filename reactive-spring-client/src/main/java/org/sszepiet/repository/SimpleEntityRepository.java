package org.sszepiet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sszepiet.entity.SimpleEntity;

@Repository
public interface SimpleEntityRepository extends JpaRepository<SimpleEntity, Long> {
}
