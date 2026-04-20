package com.ranger.auth_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an external authentication provider linked to a user.
 *
 * <p>Supports social login providers like:
 * - Google
 * - Facebook
 * - Microsoft
 *
 * <p>This allows a single user to log in using multiple providers.
 */
@Entity
@Table(
        name = "auth_providers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider", "providerUserId"})
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthProvider {

    /**
     * Primary identifier (UUID)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Owning user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Provider type (GOOGLE, FACEBOOK, MICROSOFT)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    /**
     * Unique ID from provider (Google ID, Facebook ID, etc.)
     */
    @Column(nullable = false)
    private String providerUserId;

    /**
     * Audit field
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * Set creation timestamp automatically
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}