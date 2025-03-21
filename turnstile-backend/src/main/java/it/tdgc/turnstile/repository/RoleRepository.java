package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}