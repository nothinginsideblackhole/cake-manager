package com.santosh.cakemanager.repository;

import com.santosh.cakemanager.model.Cake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

@DataJpaTest
class CakeRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CakeRepository cakeRepository;

    @Test
    void findAllEmpty() {
        List<Cake> all = cakeRepository.findAll();
        Assertions.assertTrue(all.isEmpty());
    }

    @Test
    void save() {
        Cake cake = new Cake("title", "desc", "url");
        Cake save = cakeRepository.save(cake);
        Assertions.assertNotNull(save);
        Assertions.assertNotNull(save.getId());
        Assertions.assertEquals(save.getTitle(), cake.getTitle());
        Assertions.assertEquals(save.getDescription(), cake.getDescription());
        Assertions.assertEquals(save.getImageLink(), cake.getImageLink());
    }

    @Test
    void getById() {
        Cake cake = new Cake("title", "desc", "url");
        Cake save = cakeRepository.save(cake);
        Cake referenceById = cakeRepository.getReferenceById(save.getId());
        Assertions.assertEquals(referenceById.getTitle(), save.getTitle());
        Assertions.assertEquals(referenceById.getDescription(), save.getDescription());
        Assertions.assertEquals(referenceById.getImageLink(), save.getImageLink());
    }

    @Test
    void getByTitle() {
        Cake cake = new Cake("title", "desc", "url");
        Cake save = cakeRepository.save(cake);
        Cake cakeByTitle = cakeRepository.getCakeByTitle(cake.getTitle()).get();
        Assertions.assertEquals(cakeByTitle.getTitle(), save.getTitle());
        Assertions.assertEquals(cakeByTitle.getDescription(), save.getDescription());
        Assertions.assertEquals(cakeByTitle.getImageLink(), save.getImageLink());
    }
}