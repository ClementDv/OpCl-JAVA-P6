package com.example.paymybuddy.paymybuddy.repository;

import com.example.paymybuddy.paymybuddy.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsDistinctByEmailEquals(String email);

    void deleteUserByEmailEquals(String email);
}
