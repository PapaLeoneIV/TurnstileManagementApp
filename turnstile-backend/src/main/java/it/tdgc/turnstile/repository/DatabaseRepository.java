package it.tdgc.turnstile.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<String> getTableNames() {
        return entityManager.createNativeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public'").getResultList();
    }
}