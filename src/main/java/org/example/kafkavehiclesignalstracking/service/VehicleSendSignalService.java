package org.example.kafkavehiclesignalstracking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kafkavehiclesignalstracking.config.KafkaConfig;
import org.example.kafkavehiclesignalstracking.model.VehicleSignal;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class VehicleSendSignalService {

    private final KafkaConfig kafkaConfig;
    private final KafkaTemplate<String, VehicleSignal> kafkaTemplate;

    public void sendSignal(VehicleSignal signal) {
        log.info("Sending the signal {} to kafka topic {}", signal, kafkaConfig.getInputTopic());

        kafkaTemplate.send(kafkaConfig.getInputTopic(), signal.getVehicleId(), signal);
    }

}
