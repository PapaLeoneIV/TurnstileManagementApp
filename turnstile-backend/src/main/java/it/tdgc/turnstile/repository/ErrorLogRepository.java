package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.ErrorLog;

@Repository
public interface ErrorLogRepository extends JpaRepository<ErrorLog, Integer> {

}