package fr.carrefour.delivery.validators;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;
import fr.carrefour.delivery.DTO.ClientDTO;

public class ClientValidator {

    public static List<String> validate(ClientDTO clientDTO) {
        List<String> errors = new ArrayList<>();

    if (clientDTO == null) {
      errors.add("Veuillez renseigner le nom");
      errors.add("Veuillez renseigner le prenom'");
      errors.add("Veuillez renseigner l'email");
      return errors;
    }
    if (!StringUtils.hasLength(clientDTO.getEmail())) {
      errors.add("Veuillez renseigner l'email");
    }
    if (!StringUtils.hasLength(clientDTO.getNom())) {
      errors.add("Veuillez renseigner le nom");
    }
    if (!StringUtils.hasLength(clientDTO.getPrenom())) {
      errors.add("Veuillez renseigner le prenom");
    }
    return errors;
    }
}
