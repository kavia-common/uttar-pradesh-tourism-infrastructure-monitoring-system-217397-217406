package com.example.demo.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Role entity representing a set of permissions, e.g., 'ADMIN', 'INSPECTOR', etc.
 */
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 60)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions",
        joinColumns = @JoinColumn(name="role_id"),
        inverseJoinColumns = @JoinColumn(name="permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    public Role() {}

    public Role(String name) { this.name = name; }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // PUBLIC_INTERFACE
    public String getName() { return name; }

    // PUBLIC_INTERFACE
    public void setName(String name) { this.name = name; }

    // PUBLIC_INTERFACE
    public Set<Permission> getPermissions() { return permissions; }

    // PUBLIC_INTERFACE
    public void setPermissions(Set<Permission> permissions) { this.permissions = permissions; }
}
