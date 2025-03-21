package it.tdgc.turnstile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.tdgc.turnstile.model.Users;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    @Query("SELECT u FROM Users u WHERE u.company.name = :companyName")
    List<Users> getUsersByCompanyName(@Param("companyName") String companyName);

    @Query("SELECT u FROM Users u WHERE u.id = :id AND u.usertype = :usertype")
    Users findUsersByIdAndUserType(@Param("id") Integer id, @Param("usertype") String usertype);

    @Query("SELECT u FROM Users u WHERE u.id = :id")
    Users findUsersById(@Param("id") Integer id);

    @Query("SELECT u FROM Users u WHERE u.badge.id = :id")
    Optional<Users> findUsersByBadgeId(@Param("id") Integer id);

    @Query("SELECT u FROM Users u WHERE u.usertype = 'Employee'")
    List<Users> findAllEmployees();

    @Query("SELECT u FROM Users u WHERE u.usertype = 'Visitor'")
    List<Users> findAllVisitors();

    @Query("SELECT u FROM Users u WHERE u.role.description = :role AND u.usertype = :userType")
    List<Users> findByRole(@Param("role") String role, @Param("userType") String userType);

    @Query("SELECT u FROM Users u WHERE u.company.name = :name AND u.usertype = :userType")
    List<Users> findByCompany(@Param("name") String name, @Param("userType") String userType);
}