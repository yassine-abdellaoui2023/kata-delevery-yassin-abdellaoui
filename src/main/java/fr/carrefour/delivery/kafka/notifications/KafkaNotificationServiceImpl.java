package fr.carrefour.delivery.kafka.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fr.carrefour.delivery.dtos.NotificationDto;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import fr.carrefour.delivery.exceptions.MapperException;
@Service
public class KafkaNotificationServiceImpl implements KafkaNotificationService {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final BrokerProducerService brokerProducerService;
    private final Environment env;

    public KafkaNotificationServiceImpl(BrokerProducerService brokerProducerService, Environment env) {
        this.brokerProducerService = brokerProducerService;
        this.env = env;
    }

    @Override
    public void send(NotificationDto notification) {
        brokerProducerService.sendMessage("notification", toJson(notification));
    }


    /**
     * Convert Object to json
     *
     * @param object object
     * @return string json
     */
    private <T> String toJson(T object) {
        try {
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new MapperException(e.getMessage());
        }
    }
}
