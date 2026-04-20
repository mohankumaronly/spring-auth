package com.ranger.auth_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a user's device/session.
 *
 * <p>Used for:
 * - Tracking login devices
 * - Managing sessions
 * - Logout from specific device
 * - Security monitoring (IP/location changes)
 */
@Entity
@Table(name = "devices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Device {

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
     * Device details (optional but useful)
     */
    private String deviceName;   // e.g. "Chrome on Windows"
    private String deviceType;   // e.g. "WEB", "MOBILE"

    /**
     * Network details
     */
    private String ipAddress;
    private String location;     // e.g. "Bangalore, India"

    /**
     * Session tracking
     */
    private LocalDateTime lastActiveAt;

    /**
     * Indicates if device/session is active
     */
    private boolean isActive = true;

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