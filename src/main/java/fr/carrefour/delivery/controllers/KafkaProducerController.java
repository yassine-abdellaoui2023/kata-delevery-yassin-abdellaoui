package fr.carrefour.delivery.controllers;

import fr.carrefour.delivery.dtos.NotificationDto;
import fr.carrefour.delivery.kafka.notifications.KafkaNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
@Tag(name = "Kafka Producer", description = "Endpoints for producing Kafka messages")
public class KafkaProducerController {

    private final KafkaNotificationService kafkaNotificationService;

    @Operation(summary = "Send a notification via Kafka")
    @ApiResponse(responseCode = "200", description = "Notification sent successfully")
    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @PostMapping
    public ResponseEntity<String> send(@RequestBody NotificationDto notificationDto) {
        kafkaNotificationService.send(notificationDto);
        return ResponseEntity.ok("Notification sent");
    }
}
