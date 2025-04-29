package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.Turnstile;

import java.util.Optional;

@Repository
public interface TurnstileRepository extends JpaRepository<Turnstile, Integer> {
    @Query("SELECT t from Turnstile t WHERE t.id = :id")
    Optional<Turnstile> findById(Integer id);
}