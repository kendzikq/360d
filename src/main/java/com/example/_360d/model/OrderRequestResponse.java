package com.example._360d.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestResponse {

    @Schema(description = "status of the requested order", example = "REGISTERED")
    private RegistrationStatus status;

    public enum RegistrationStatus {
        REGISTERED,
        REJECTED
    }
}
