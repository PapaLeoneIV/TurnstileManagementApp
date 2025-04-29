package it.tdgc.turnstile.repository;

import it.tdgc.turnstile.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.Company;

import java.util.Optional;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    @Query("SELECT c from Company c WHERE c.name = :name")
    Optional<Company> findByName(String name);

    @Query("SELECT c from Company c WHERE c.address = :address")
    Optional<Company> findByAddress(String address);
}

