package org.example.kafkavehiclesignalstracking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleCurrentDistanceInformation {

    private String vehicleId;
    private Coordinates coordinates;
    private double distance;
}
