package fr.carrefour.delivery.validators;

import java.util.ArrayList;
import java.util.List;

import fr.carrefour.delivery.dtos.DeliveryDto;
import fr.carrefour.delivery.entities.enums.DeliveryMode;

public class DeliveryValidator {

    public static List<String> validate(DeliveryDto deliveryDto) {
        System.out.println(deliveryDto);
        List<String> errors = new ArrayList<>();

    if (deliveryDto == null) {
      errors.add("Veuillez renseigner le mode de livraison");
      errors.add("Veuillez renseigner la date de livraison");
      errors.add("Veuillez renseigner l'id client");
      return errors;
    }
    if (deliveryDto.getDateDeLivraison() == null) {
      errors.add("Veuillez renseigner une date de livraison valide");
    }
    if (deliveryDto.getDeliveryMode() != DeliveryMode.DELIVERY
        && deliveryDto.getDeliveryMode() != DeliveryMode.DELIVERY_ASAP
        && deliveryDto.getDeliveryMode() != DeliveryMode.DELIVERY_TODAY
        && deliveryDto.getDeliveryMode() != DeliveryMode.DRIVE
        ) {
      errors.add("Veuillez renseigner une mode de livraison valide");
    }
    if (deliveryDto.getClientDTO().getId() == null) {
      errors.add("Veuillez renseigner un id client valide");
    }
    return errors;
    }
}
