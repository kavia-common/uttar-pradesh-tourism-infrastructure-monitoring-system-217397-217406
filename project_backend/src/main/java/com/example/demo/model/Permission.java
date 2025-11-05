package com.example.demo.model;

import jakarta.persistence.*;

/**
 * Permission entity represents a fine-grained permission string, e.g., 'PROJECT_READ'.
 */
@Entity
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    public Permission() {}

    public Permission(String name) {
        this.name = name;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    // PUBLIC_INTERFACE
    public String getName() { return name; }

    // PUBLIC_INTERFACE
    public void setName(String name) { this.name = name; }
}
