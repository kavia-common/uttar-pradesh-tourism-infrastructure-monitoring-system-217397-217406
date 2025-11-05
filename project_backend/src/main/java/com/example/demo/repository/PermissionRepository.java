package com.example.demo.repository;

import com.example.demo.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Permission entity.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    // PUBLIC_INTERFACE
    Optional<Permission> findByName(String name);
}
