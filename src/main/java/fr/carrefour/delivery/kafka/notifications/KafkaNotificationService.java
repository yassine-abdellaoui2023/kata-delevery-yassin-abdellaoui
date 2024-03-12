package fr.carrefour.delivery.kafka.notifications;
import fr.carrefour.delivery.dtos.NotificationDto;


public interface KafkaNotificationService {

    /**
     * Send notification
     * @param notification model of notification
     */
    void send(NotificationDto notification);
}
