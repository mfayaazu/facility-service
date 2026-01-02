package com.infinotive.facility.domain.repository;

import com.infinotive.facility.domain.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, String> {

    Optional<Location> findByTenant_IdAndCode(String tenantId, String code);

    List<Location> findAllByTenant_Id(String tenantId);
}
