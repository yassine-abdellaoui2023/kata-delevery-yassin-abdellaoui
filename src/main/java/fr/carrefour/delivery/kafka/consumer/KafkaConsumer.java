package fr.carrefour.delivery.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.carrefour.delivery.dtos.NotificationDto;
import fr.carrefour.delivery.exceptions.MapperException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "notification", groupId = "notification-group-id", containerFactory = "kakfaListenerContainerFactory")
    public void listenSenderEmail(String data) {

        NotificationDto notification = fromJson(data, NotificationDto.class);

        log.info("Consumed message: {} : testing" , notification);

    }

    /**
     * Convert json to Object
     * @param json String json value
     * @param clazz Instances of the class
     * @param <T> Object Class
     * @return Object class
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        try {
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }
}
