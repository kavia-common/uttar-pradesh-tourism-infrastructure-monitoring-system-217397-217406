package com.example.demo.model.domain;

import jakarta.persistence.*;

/**
 * Contractor/vendor profile.
 */
@Entity
@Table(name = "contractors")
public class Contractor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=200)
    private String name;

    @Column(length=200)
    private String contactPerson;

    @Column(length=200)
    private String email;

    @Column(length=50)
    private String phone;

    @Column(length=500)
    private String address;

    @Column(length=50)
    private String status; // ACTIVE/INACTIVE

    public Contractor() {}

    // PUBLIC_INTERFACE
    public Long getId() { return id; }
    // PUBLIC_INTERFACE
    public void setId(Long id) { this.id = id; }
    // PUBLIC_INTERFACE
    public String getName() { return name; }
    // PUBLIC_INTERFACE
    public void setName(String name) { this.name = name; }
    // PUBLIC_INTERFACE
    public String getContactPerson() { return contactPerson; }
    // PUBLIC_INTERFACE
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    // PUBLIC_INTERFACE
    public String getEmail() { return email; }
    // PUBLIC_INTERFACE
    public void setEmail(String email) { this.email = email; }
    // PUBLIC_INTERFACE
    public String getPhone() { return phone; }
    // PUBLIC_INTERFACE
    public void setPhone(String phone) { this.phone = phone; }
    // PUBLIC_INTERFACE
    public String getAddress() { return address; }
    // PUBLIC_INTERFACE
    public void setAddress(String address) { this.address = address; }
    // PUBLIC_INTERFACE
    public String getStatus() { return status; }
    // PUBLIC_INTERFACE
    public void setStatus(String status) { this.status = status; }
}
