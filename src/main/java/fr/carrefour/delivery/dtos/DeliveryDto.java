package fr.carrefour.delivery.dtos;

import java.util.Date;

import fr.carrefour.delivery.entities.Delivery;
import fr.carrefour.delivery.entities.enums.DeliveryMode;
import fr.carrefour.delivery.exceptions.EntityNotFoundException;
import fr.carrefour.delivery.services.interfaces.IClientService;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;


@Builder
@Data
@ToString
public class DeliveryDto {


    private DeliveryMode deliveryMode;
    private Date dateDeLivraison;
    private ClientDTO clientDTO;

    public static DeliveryDto fromEntity(Delivery delivery, IClientService clientService) throws EntityNotFoundException {
        if (delivery == null) {
          return null;
        }
    
        return DeliveryDto.builder()
            .deliveryMode(delivery.getDeliveryMode())
            .dateDeLivraison(delivery.getDeliveryDate())
            .clientDTO(clientService.getClientById(delivery.getClientId()))
            .build();
      }
    
      public static Delivery toEntity(DeliveryDto deliveryDto) {
        if (deliveryDto == null) {
          return null;
        }
        Delivery delivery = new Delivery();
        delivery.setDeliveryMode(deliveryDto.getDeliveryMode());
        delivery.setDeliveryDate(deliveryDto.getDateDeLivraison());
         delivery.setClientId(deliveryDto.getClientDTO().getId());
        return delivery;
      }
      
}