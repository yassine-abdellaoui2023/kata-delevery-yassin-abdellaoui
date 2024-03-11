package fr.carrefour.delivery.DTO;

import java.util.Date;

import fr.carrefour.delivery.entities.Livraison;
import fr.carrefour.delivery.enums.ModeDeLivraison;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class LivraisonDTO {

    private ModeDeLivraison modeDeLivraison;
    private Date dateDeLivraison;
    private Integer idClient;

    public static LivraisonDTO fromEntity(Livraison livraison) {
        if (livraison == null) {
          return null;
        }
    
        return LivraisonDTO.builder()
            .modeDeLivraison(livraison.getModeDeLivraison())
            .dateDeLivraison(livraison.getDateDeLivraison())
            .idClient(livraison.getClient().getIdClient())
            .build();
      }
    
      public static Livraison toEntity(LivraisonDTO livraisonDTO) {
        if (livraisonDTO == null) {
          return null;
        }
        Livraison livraison = new Livraison();
        livraison.setModeDeLivraison(livraisonDTO.getModeDeLivraison());
        livraison.setDateDeLivraison(livraisonDTO.getDateDeLivraison());
        // livraison.setClient(livraisonDTO.getIdClient());
        return livraison;
      }
      
}