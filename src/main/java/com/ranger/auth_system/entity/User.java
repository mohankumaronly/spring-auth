package com.ranger.auth_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Represents a user in the authentication system.
 *
 * <p>This entity supports:
 * - Email/password authentication
 * - Social login (via AuthProvider)
 * - Multi-device tracking
 * - Token-based authentication (refresh tokens)
 * - Security tracking and auditing
 *
 * <p>Production-ready features:
 * - UUID primary key
 * - Soft delete support
 * - Audit fields
 * - Relationship mappings
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Primary identifier (UUID for security and scalability)
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Unique email address used for login
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Hashed password (nullable for social login users)
     */
    @Column(nullable = true)
    private String password;

    private String firstName;
    private String lastName;

    /**
     * Indicates whether email is verified via OTP
     */
    @Column(nullable = false)
    private boolean emailVerified = false;

    /**
     * Account status (ACTIVE, SUSPENDED, etc.)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus accountStatus;

    /**
     * Security tracking fields
     */
    private int failedLoginAttempts;

    private LocalDateTime lastLoginAt;
    private String lastLoginIp;

    /**
     * Used to invalidate tokens after password change
     */
    private LocalDateTime passwordChangedAt;

    /**
     * Audit fields
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    /**
     * Social login providers linked to this user
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AuthProvider> providers;

    /**
     * Devices associated with this user
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;

    /**
     * Refresh tokens for session management
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RefreshToken> refreshTokens;

    /**
     * Automatically set timestamps before persisting
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Automatically update timestamp on update
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}