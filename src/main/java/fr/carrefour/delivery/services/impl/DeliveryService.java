package fr.carrefour.delivery.services.impl;

import java.util.Date;
import java.util.List;

import fr.carrefour.delivery.dtos.DeliveryDto;
import fr.carrefour.delivery.entities.enums.DeliveryMode;
import fr.carrefour.delivery.exceptions.EntityNotFoundException;
import fr.carrefour.delivery.services.interfaces.IClientService;
import fr.carrefour.delivery.services.interfaces.IDeliveryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import fr.carrefour.delivery.entities.Delivery;
import fr.carrefour.delivery.repositories.DeliveryRepository;

@Service
@RequiredArgsConstructor
public class DeliveryService implements IDeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final IClientService clientService;

    @Override
    @Transactional
    public String saveDelevery(DeliveryDto deliveryDto) {
                if ((deliveryDto.getDateDeLivraison().getHours() < new Date().getHours() + 5 || deliveryDto.getDateDeLivraison().getDay() > new Date().getDay())&& deliveryDto.getDeliveryMode() != DeliveryMode.DELIVERY_ASAP) {
                return "You should choose 'DELIVERY_ASAP'";
            }if (deliveryDto.getDateDeLivraison().getDay() == new Date().getDay() && deliveryDto.getDeliveryMode() != DeliveryMode.DELIVERY_TODAY && deliveryDto.getDeliveryMode() != DeliveryMode.DELIVERY_ASAP){
            return "You should choose 'DELIVERY_TODAY' mode";}
                else if(deliveryDto.getDateDeLivraison().getDay() != new Date().getDay() && deliveryDto.getDeliveryMode() == DeliveryMode.DELIVERY_ASAP){
                    return "you should choose another mode";
        } else if (checkSlotAvailability(deliveryDto.getDateDeLivraison(), deliveryDto.getDeliveryMode())) {
                deliveryRepository.save(DeliveryDto.toEntity(deliveryDto));
            }else {
                return "Date in not available";
            }
        return "Delivery saved";
    }

    public List<DeliveryDto> getAllDeliveries() {
        return deliveryRepository.findAll().stream().map(d -> {
            try {
                return DeliveryDto.fromEntity(d,clientService);
            } catch (EntityNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    @Override
    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }


    private boolean checkSlotAvailability(Date date, DeliveryMode deliveryMode) {
        // Consultez la base de données pour voir si le créneau est déjà réservé pour ce mode de livraison
        // Vous devrez utiliser les informations du mode de livraison (par exemple, le type de livraison, la date, etc.)
        // pour effectuer la vérification appropriée
        // Retournez true si le créneau est disponible, sinon false

        // Exemple de pseudo-code pour une vérification simple (vous devez adapter à votre modèle de données réel)
        List<Delivery> deliveries = deliveryRepository.findAllByDeliveryDateAndDeliveryMode(date, deliveryMode);
        return deliveries.isEmpty(); // Le créneau est disponible s'il n'y a aucune livraison prévue à ce moment-là
    }
}
