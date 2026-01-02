package com.infinotive.facility.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Physical place inside a tenant environment (room, hall, corridor, etc.).
 * Each location has a unique code per tenant and can be used to report issues.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "locations",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_location_tenant_code",
                columnNames = {"tenant_id", "code"}
        )
)
public class Location extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "tenant_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_location_tenant")
    )
    private Tenant tenant;

    @Column(name = "code", nullable = false, length = 64)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    public Location(Tenant tenant, String code, String name, String description) {
        this.tenant = tenant;
        this.code = code;
        this.name = name;
        this.description = description;
    }
}
