package fr.carrefour.delivery.validators;

import java.util.ArrayList;
import java.util.List;

import fr.carrefour.delivery.DTO.LivraisonDTO;
import fr.carrefour.delivery.enums.ModeDeLivraison;

public class LivraisonValidator {

    public static List<String> validate(LivraisonDTO livraisonDTO) {
        List<String> errors = new ArrayList<>();

    if (livraisonDTO == null) {
      errors.add("Veuillez renseigner le mode de livraison");
      errors.add("Veuillez renseigner la date de livraison");
      errors.add("Veuillez renseigner l'id client");
      return errors;
    }
    if (livraisonDTO.getDateDeLivraison() == null) {
      errors.add("Veuillez renseigner une date de livraison valide");
    }
    if (livraisonDTO.getModeDeLivraison() != ModeDeLivraison.DELIVERY
        && livraisonDTO.getModeDeLivraison() != ModeDeLivraison.DELIVERY_ASAP
        && livraisonDTO.getModeDeLivraison() != ModeDeLivraison.DELIVERY_TODAY
        && livraisonDTO.getModeDeLivraison() != ModeDeLivraison.DRIVE
        ) {
      errors.add("Veuillez renseigner une mode de livraison valide");
    }
    if (livraisonDTO.getIdClient() == null) {
      errors.add("Veuillez renseigner un id client valide");
    }
    return errors;
    }
}
