package fr.carrefour.delivery.kafka.notifications;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BrokerProducerService {

    private final KafkaTemplate<Integer, String> kafkaTemplate;

    public BrokerProducerService(KafkaTemplate<Integer, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }
}
