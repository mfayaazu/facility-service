package com.infinotive.facility.domain.repository;

import com.infinotive.facility.domain.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, String> {

    Optional<Tenant> findByCodeAndActiveTrue(String code);
}
