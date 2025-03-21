package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.TransactionEvent;

import java.util.List;

@Repository
public interface TransactionEventRepository extends JpaRepository<TransactionEvent, Integer> {

    @Query("SELECT te FROM TransactionEvent te WHERE te.id = :id")
    List<TransactionEvent> findTransactionEventWithTransactionId(@Param("id") Integer id);

    @Query("SELECT te FROM TransactionEvent te WHERE te.transaction.id = :id ORDER BY te.created_at DESC LIMIT 1")
    TransactionEvent findLastTransactionEventOfSpecTransaction(Integer id);
}