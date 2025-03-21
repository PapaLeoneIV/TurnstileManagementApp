package it.tdgc.turnstile.repository;

import it.tdgc.turnstile.model.Users;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.Transaction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM Transaction t WHERE t.date = :day AND t.user.id = :userId")
    List<Transaction>getTransactionWithUserId(@Param("day") LocalDate day, @Param("userId") Integer userId);

    @Query("SELECT t FROM Transaction t WHERE t.date = :day AND t.user.company.name = :companyName")
    List<Transaction>getTransactionByCompany(@Param("day") LocalDate day, @Param("companyName") String companyName);

    @Query("SELECT t FROM Transaction t WHERE t.date = :day AND t.user.role.description = :role")
    List<Transaction>getTransactionByRole(@Param("day") LocalDate day, @Param("role") String role);

    @Query("SELECT t FROM Transaction t WHERE t.date = :day AND t.time >= :start AND t.time <= :end")
    List<Transaction> findWithHourRange(@Param("day") LocalDate day, @Param("start") LocalTime start, @Param("end") LocalTime end);

    @Query("SELECT t FROM Transaction t WHERE t.date = :date")
    List<Transaction> findWithDate(@Param("date") LocalDate date);

    @Query("SELECT t FROM Transaction t WHERE t.date >= :dateStart AND t.date <= :dateEnd")
    List<Transaction> findWithDateRange(@Param("dateStart") LocalDate dateStart, @Param("dateEnd")LocalDate dateEnd);

    List<Transaction> user(Users user);
}