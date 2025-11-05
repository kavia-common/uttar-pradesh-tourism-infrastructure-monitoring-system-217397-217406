package com.example.demo.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * User entity with roles for RBAC.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 80)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // PUBLIC_INTERFACE
    public String getUsername() { return username; }

    // PUBLIC_INTERFACE
    public void setUsername(String username) { this.username = username; }

    // PUBLIC_INTERFACE
    public String getPassword() { return password; }

    // PUBLIC_INTERFACE
    public void setPassword(String password) { this.password = password; }

    // PUBLIC_INTERFACE
    public boolean isEnabled() { return enabled; }

    // PUBLIC_INTERFACE
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    // PUBLIC_INTERFACE
    public Set<Role> getRoles() { return roles; }

    // PUBLIC_INTERFACE
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
