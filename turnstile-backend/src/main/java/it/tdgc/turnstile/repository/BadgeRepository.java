package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.Badge;
import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Integer> {

    @Query("SELECT b FROM Badge b WHERE b.rfid = :rfid")
    Optional<Badge> findByRfid(@Param("rfid") String rfid);
}