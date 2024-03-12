package fr.carrefour.delivery.controllers;

import fr.carrefour.delivery.dtos.NotificationDto;
import fr.carrefour.delivery.kafka.notifications.KafkaNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaProducerController {

    private final KafkaNotificationService kafkaNotificationService;



    @PostMapping
    public ResponseEntity<String> send(@RequestBody NotificationDto notificationDto){
        kafkaNotificationService.send(notificationDto);
        return ResponseEntity.ok("notification sent");
    }
}
