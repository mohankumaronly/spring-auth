package com.ranger.auth_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a refresh token used for session management.
 *
 * <p>Used for:
 * - Generating new access tokens
 * - Managing user sessions across devices
 * - Token revocation and rotation
 */
@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    /**
     * Primary identifier (UUID)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Associated user
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Token value (secure random string / JWT)
     */
    @Column(nullable = false, length = 500)
    private String token;

    /**
     * Indicates whether token is revoked
     */
    private boolean isRevoked = false;

    /**
     * Expiration timestamp
     */
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    /**
     * Audit field
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * Auto set creation time
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}