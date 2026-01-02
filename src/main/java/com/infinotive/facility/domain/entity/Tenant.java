package com.infinotive.facility.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents an organization (company, school, municipality)
 * that owns data in the system. Used for multi-tenancy isolation.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "tenants",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_tenant_code",
                columnNames = "code"
        )
)
public class Tenant extends BaseEntity {

    @Column(name = "code", nullable = false, length = 64)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public Tenant(String code, String name) {
        this.code = code;
        this.name = name;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }
}
