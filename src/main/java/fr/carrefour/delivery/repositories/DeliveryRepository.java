package fr.carrefour.delivery.repositories;

import fr.carrefour.delivery.entities.Delivery;
import fr.carrefour.delivery.entities.enums.DeliveryMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByClientId(String clientId);
    List<Delivery> findAllByDeliveryDateAndDeliveryMode(Date date, DeliveryMode deliveryMode);
}
