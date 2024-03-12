package fr.carrefour.delivery.services.interfaces;

import fr.carrefour.delivery.dtos.DeliveryDto;

import java.util.List;

public interface IDeliveryService {
    String saveDelevery(DeliveryDto deliveryDto);
    List<DeliveryDto> getAllDeliveries();

    void deleteDelivery(Long id);
}
