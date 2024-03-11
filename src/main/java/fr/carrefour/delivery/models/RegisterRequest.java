package fr.carrefour.delivery.models;

import fr.carrefour.delivery.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String prenom;
  private String nom;
  private String email;
  private String password;
  private Role role;
}
