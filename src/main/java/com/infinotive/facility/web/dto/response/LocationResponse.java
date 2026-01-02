package com.infinotive.facility.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API response for location details.
 */
@Getter
@AllArgsConstructor
public class LocationResponse {

    private String id;
    private String code;
    private String name;
    private String description;
}
