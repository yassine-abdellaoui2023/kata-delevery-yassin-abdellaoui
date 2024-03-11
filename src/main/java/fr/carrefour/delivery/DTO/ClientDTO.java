package fr.carrefour.delivery.DTO;


import fr.carrefour.delivery.entities.Client;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientDTO {

    private String nom;

    private String prenom;

    private String email;

    public static ClientDTO fromEntity(Client client) {
        if (client == null) {
          return null;
        }
    
        return ClientDTO.builder()
            .nom(client.getNom())
            .prenom(client.getPrenom())
            .email(client.getEmail())
            .build();
      }
    
      public static Client toEntity(ClientDTO clientDTO) {
        if (clientDTO == null) {
          return null;
        }
        Client client = new Client();
        client.setEmail(clientDTO.getEmail());
        client.setNom(clientDTO.getNom());
        client.setPrenom(clientDTO.getPrenom());
        return client;
      }

}
