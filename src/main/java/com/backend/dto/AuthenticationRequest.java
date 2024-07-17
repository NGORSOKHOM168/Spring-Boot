package com.backend.dto;

import lombok.Builder;

@Builder
public record AuthenticationRequest(
        String email,
        String password
) {

}
