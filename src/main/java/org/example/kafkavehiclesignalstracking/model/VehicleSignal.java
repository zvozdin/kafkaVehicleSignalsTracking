package org.example.kafkavehiclesignalstracking.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleSignal {

    @NotBlank(message = "The vehicleId is required.")
    private String vehicleId;

    @Valid
    @NotNull(message = "The coordinates are required.")
    private Coordinates coordinates;
}
