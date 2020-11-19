package com.example.paymybuddy.paymybuddy.repository;

import com.example.paymybuddy.paymybuddy.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailEquals(String email);

    boolean existsDistinctByEmailEquals(String email);

    void deleteUserByEmailEquals(String email);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
