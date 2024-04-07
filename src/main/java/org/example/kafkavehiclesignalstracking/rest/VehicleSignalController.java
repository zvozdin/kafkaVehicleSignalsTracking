package org.example.kafkavehiclesignalstracking.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kafkavehiclesignalstracking.model.VehicleSignal;
import org.example.kafkavehiclesignalstracking.service.VehicleSendSignalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/vehicle/signal")
@RequiredArgsConstructor
public class VehicleSignalController {

    private final VehicleSendSignalService vehicleSendSignalService;

    @PostMapping
    public ResponseEntity<Void> add(@Valid @RequestBody VehicleSignal signal) {
        log.info("Received signal from the vehicle: vehicleId={}, coordinates={}",
                signal.getVehicleId(), signal.getCoordinates());

        vehicleSendSignalService.sendSignal(signal);

        return ResponseEntity.ok().build();
    }

}
