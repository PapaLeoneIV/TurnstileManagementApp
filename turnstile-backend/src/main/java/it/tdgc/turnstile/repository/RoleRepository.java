package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.Role;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r from Role r WHERE r.level= :level")
    Optional<Role> findByLevel(int level);

    @Query("SELECT r from Role r WHERE r.description= :description")
    Optional<Role> findByDescription(String description);
}