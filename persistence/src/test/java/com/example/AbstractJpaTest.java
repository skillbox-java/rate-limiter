package com.example;

import com.example.config.PersistenceTestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@DataJpaTest                         // включает JPA-срез + TestEntityManager
@ActiveProfiles("test")              // application-test.yml
@ContextConfiguration(classes = PersistenceTestConfig.class)
public abstract class AbstractJpaTest {

    @Autowired
    protected TestEntityManager em;  // доступен во всех наследниках
}
