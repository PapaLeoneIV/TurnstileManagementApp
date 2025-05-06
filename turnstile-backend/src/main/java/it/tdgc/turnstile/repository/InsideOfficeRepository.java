package it.tdgc.turnstile.repository;

import it.tdgc.turnstile.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.InsideOffice;

import java.util.Optional;

@Repository
public interface InsideOfficeRepository extends JpaRepository<InsideOffice, Integer> {


    @Query("SELECT io FROM InsideOffice io WHERE io.user.id = :id")
    Optional<InsideOffice> findByUserId(@Param("id") Integer id);

}