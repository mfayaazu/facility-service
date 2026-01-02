package com.infinotive.facility.domain.entity;

import com.infinotive.facility.enums.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an application user belonging to a tenant.
 * Authentication is based on email + password hash.
 * Role controls permissions inside the system.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_user_tenant_email",
                columnNames = {"tenant_id", "email"}
        )
)
public class User extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tenant_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_tenant")
    )
    private Tenant tenant;

    @Column(name = "email", nullable = false, length = 320)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 32)
    private UserRole role;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public User(Tenant tenant, String email, String passwordHash, UserRole role) {
        this.tenant = tenant;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
