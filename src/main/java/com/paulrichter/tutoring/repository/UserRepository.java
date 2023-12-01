package com.paulrichter.tutoring.repository;

import com.paulrichter.tutoring.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameIgnoreCase(String username);
    Boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM user WHERE username = :mainUserName", nativeQuery = true)
    User findMainTutor(String mainUserName);
}