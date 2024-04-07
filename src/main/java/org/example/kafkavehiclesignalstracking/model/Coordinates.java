package org.example.kafkavehiclesignalstracking.model;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class Coordinates {

    @PositiveOrZero(message = "[Coordinates] The x is required and must be positive or zero.")
    private int x;
    @PositiveOrZero(message = "[Coordinates] The y is required and must be positive or zero.")
    private int y;
}
