package com.backend.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EmailRequest(
        @Email
        @NotNull
        String email
) {
}
