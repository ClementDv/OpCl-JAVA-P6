package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findById(Long id);

    @Query("select id from User where email = ?1")
    Long findIdByEmail(String contactEmail);

    @Query("select balance from User where id = ?1")
    double findBalanceById(Long id);

    @Modifying
    @Query("update User set balance = ?1 where id = ?2")
    void updateBalanceById(Double amount, Long id);
}
