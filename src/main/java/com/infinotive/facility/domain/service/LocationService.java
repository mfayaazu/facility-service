package com.infinotive.facility.domain.service;

import com.infinotive.facility.domain.entity.Location;
import com.infinotive.facility.domain.repository.LocationRepository;
import com.infinotive.facility.domain.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final TenantRepository tenantRepository;

    public List<Location> findAllByTenant(String tenantId) {
        return locationRepository.findAllByTenant_Id(tenantId);
    }

    public Location create(String tenantId, String code, String name, String description) {
        var tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found"));

        Location location = new Location(
                tenant,
                code,
                name,
                description
        );

        return locationRepository.save(location);
    }
}
