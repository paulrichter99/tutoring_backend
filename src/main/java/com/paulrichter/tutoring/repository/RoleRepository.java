package com.paulrichter.tutoring.repository;

import com.paulrichter.tutoring.Enum.ERole;
import com.paulrichter.tutoring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

    Optional<Role> findOneByName(ERole roleUser);
}