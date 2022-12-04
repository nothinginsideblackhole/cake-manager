package com.santosh.cakemanager.repository;

import com.santosh.cakemanager.model.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {

    @Query("SELECT c FROM Cake c WHERE c.title = ?1")
    Optional<Cake> getCakeByTitle(String title);
}