package com.infinotive.facility.web.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * Payload for creating a new location.
 */
@Getter
@Setter
public class CreateLocationRequest {
    private String code;
    private String name;
    private String description;
}
