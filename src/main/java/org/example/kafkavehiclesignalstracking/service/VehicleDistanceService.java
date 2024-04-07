package org.example.kafkavehiclesignalstracking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kafkavehiclesignalstracking.model.VehicleCurrentDistanceInformation;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class VehicleDistanceService {

    @KafkaListener(
            topics = "${kafka.topic.output}",
            groupId = "${spring.kafka.consumer.group-id.id.distance}",
            concurrency = "3"
    )
    public void receive(VehicleCurrentDistanceInformation currentDistanceInformation) {
        log.info("Vehicle {} travelled the distance={}",
                currentDistanceInformation.getVehicleId(), currentDistanceInformation.getDistance());
    }

}
