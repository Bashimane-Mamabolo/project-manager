package com.bash.pmbackend.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
