package com.ranger.auth_system.entity;

/**
 * Represents the lifecycle state of a user account.
 */
public enum AccountStatus {
    ACTIVE,
    PENDING_VERIFICATION,
    SUSPENDED,
    DELETED
}