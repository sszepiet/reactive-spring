package org.sszepiet.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableJpaRepositories(basePackages = "org.sszepiet.repository")
@EntityScan(basePackages = "org.sszepiet.entity")
@EnableTransactionManagement
public class JpaConfig {

}
