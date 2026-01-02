package com.infinotive.facility.web.controller;

import com.infinotive.facility.domain.service.LocationService;
import com.infinotive.facility.security.CurrentUser;
import com.infinotive.facility.web.dto.request.CreateLocationRequest;
import com.infinotive.facility.web.dto.response.LocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping
    public List<LocationResponse> list() {
        var principal = CurrentUser.get();

        return locationService.findAllByTenant(principal.tenantId())
                .stream()
                .map(l -> new LocationResponse(
                        l.getId(),
                        l.getCode(),
                        l.getName(),
                        l.getDescription()
                ))
                .toList();
    }

    @PostMapping
    public LocationResponse create(@RequestBody CreateLocationRequest req) {
        var principal = CurrentUser.get();

        var saved = locationService.create(
                principal.tenantId(),
                req.getCode(),
                req.getName(),
                req.getDescription()
        );

        return new LocationResponse(
                saved.getId(),
                saved.getCode(),
                saved.getName(),
                saved.getDescription()
        );
    }
}
