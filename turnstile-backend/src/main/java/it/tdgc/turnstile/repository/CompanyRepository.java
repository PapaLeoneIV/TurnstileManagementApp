package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}