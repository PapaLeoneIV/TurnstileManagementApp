package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.Turnstile;

@Repository
public interface TurnstileRepository extends JpaRepository<Turnstile, Integer> {

}