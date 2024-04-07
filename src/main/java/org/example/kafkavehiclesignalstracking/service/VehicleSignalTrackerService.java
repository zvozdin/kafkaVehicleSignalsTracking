package org.example.kafkavehiclesignalstracking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kafkavehiclesignalstracking.config.KafkaConfig;
import org.example.kafkavehiclesignalstracking.model.Coordinates;
import org.example.kafkavehiclesignalstracking.model.VehicleCurrentDistanceInformation;
import org.example.kafkavehiclesignalstracking.model.VehicleSignal;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Service
public class VehicleSignalTrackerService {

    private final Map<String, VehicleCurrentDistanceInformation> movementHistoryCache = new ConcurrentHashMap<>();

    private final KafkaTemplate<String, VehicleCurrentDistanceInformation> kafkaTemplate;
    private final KafkaConfig kafkaConfig;

    @KafkaListener(
            topics = "${kafka.topic.input}",
            groupId = "${spring.kafka.consumer.group-id.id.signal}",
            concurrency = "3",
            properties = {"spring.json.value.default.type=org.example.kafkavehiclesignalstracking.model.VehicleSignal"}
    )
    public void receive(VehicleSignal signal, @Header(KafkaHeaders.RECEIVED_PARTITION) int partition) {
        log.info("Received message from partition:  {}", partition);
        log.info("Received vehicle signal {}", signal);

        String vehicleId = signal.getVehicleId();
        Coordinates newCoordinates = signal.getCoordinates();
        VehicleCurrentDistanceInformation currentDistanceInformation = movementHistoryCache.get(vehicleId);

        double newDistance = 0;
        if (currentDistanceInformation != null) {
            newDistance =
                    currentDistanceInformation.getDistance() + calculateDistance(currentDistanceInformation.getCoordinates(), newCoordinates);
        }

        VehicleCurrentDistanceInformation newDistanceInformation =
                new VehicleCurrentDistanceInformation(vehicleId, newCoordinates, newDistance);
        movementHistoryCache.put(vehicleId, newDistanceInformation);

        log.info("The latest distance information: vehicle id={}, newDistance={}", vehicleId, newDistance);

        kafkaTemplate.send(kafkaConfig.getOutputTopic(), vehicleId, newDistanceInformation);
    }

    private double calculateDistance(Coordinates currentCoordinates, Coordinates newCoordinates) {
        double xDifference = newCoordinates.getX() - currentCoordinates.getX();
        double yDifference = newCoordinates.getY() - currentCoordinates.getY();

        double distance = Math.hypot(xDifference, yDifference);

        // Format the result to have one digit after the decimal point
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(distance));
    }

}
