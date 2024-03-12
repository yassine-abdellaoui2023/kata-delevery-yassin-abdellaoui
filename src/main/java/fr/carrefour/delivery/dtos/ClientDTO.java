package fr.carrefour.delivery.dtos;


import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private boolean isActive;
    private String password;

    private List<RoleDto> roles;

}
