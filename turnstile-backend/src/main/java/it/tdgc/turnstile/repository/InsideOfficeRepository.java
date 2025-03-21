package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.InsideOffice;

@Repository
public interface InsideOfficeRepository extends JpaRepository<InsideOffice, Integer> {


    @Query("SELECT io FROM InsideOffice io WHERE io.user.id = :id")
    InsideOffice findByUserId(@Param("id") Integer id);

    @Query(value = "SELECT id FROM inside_office ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Integer getLastId();

//
//    @Query("SELECT COUNT(io) FROM InsideOffice io")
//    Integer getTotNumOfUsers();

}