package fr.carrefour.delivery.services.impl;

import fr.carrefour.delivery.dtos.ClientDTO;
import fr.carrefour.delivery.dtos.RoleDto;
import fr.carrefour.delivery.configurations.KeycloakConfig;
import fr.carrefour.delivery.exceptions.EntityAlreadyExistException;
import fr.carrefour.delivery.exceptions.EntityNotFoundException;
import fr.carrefour.delivery.handlers.ErrorCodes;
import fr.carrefour.delivery.services.interfaces.IClientService;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService {
    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

    @Override
    public String authAndGetToken(String username, String password) {
        return KeycloakConfig.getInstance(username , password).tokenManager().getAccessTokenString();
    }

    @Override
    public void register(ClientDTO clientDTO) throws EntityAlreadyExistException {
        UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(clientDTO.getPassword());
        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(clientDTO.getUsername());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(clientDTO.getFirstName());
        kcUser.setLastName(clientDTO.getLastName());
        kcUser.setEmail(clientDTO.getEmail());
        kcUser.setEnabled(clientDTO.isActive());
        kcUser.setEmailVerified(false);
        System.out.println(kcUser.getUsername());
        try (Response response = usersResource.create(kcUser)) {
            if (response.getStatus() != HttpStatus.CREATED.value()) {
                throw new EntityAlreadyExistException("An Error accord while saving new client maybe caused by ",
                        ErrorCodes.CLIENT_ALREADY_EXIST, Arrays.asList("Email is already exist!", "Username is already exist!"));
            }
        }
    }

    public List<ClientDTO> getAllClients() {
        UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
        List<UserRepresentation> userRepresentations = usersResource.list();

        List<RoleScopeResource> userRoleMappingResources = userRepresentations.stream()
                .map(userRepresentation -> usersResource.get(userRepresentation.getId()).roles().realmLevel())
                .collect(Collectors.toList());

        List<List<RoleRepresentation>> allUsersRoles = userRoleMappingResources.stream().map(RoleScopeResource::listAll)
                .collect(Collectors.toList());


        List<ClientDTO> users = new ArrayList<>();
        userRepresentations
                .forEach(userRepresentation -> {
                    List<RoleRepresentation> roleRepresentations = allUsersRoles.get(userRepresentations
                            .indexOf(userRepresentation));


                        List<RoleDto> roles = roleRepresentations.stream().map(roleRepresentation -> {
                            RoleDto role = new RoleDto();
                            role.setName(roleRepresentation.getName());
                            role.setId(roleRepresentation.getId());
                            return role;
                        }).collect(Collectors.toList());

                        ClientDTO user = new ClientDTO();
                        user.setId(userRepresentation.getId());
                        user.setFirstName(userRepresentation.getFirstName());
                        user.setUsername(userRepresentation.getUsername());
                        user.setEmail(userRepresentation.getEmail());
                        user.setLastName(userRepresentation.getLastName());
                        user.setRoles(roles);
                        user.setActive(userRepresentation.isEnabled());
                        users.add(user);
                });

        return users;
    }


    public ClientDTO getClientById(String userId) throws EntityNotFoundException {
        UsersResource usersResource = KeycloakConfig.getInstance().realm(KeycloakConfig.realm).users();
        UserRepresentation userRepresentation = usersResource.get(userId).toRepresentation();

        if (userRepresentation == null) {
            throw new EntityNotFoundException("Client with id "+ userId + " is not found" , ErrorCodes.ENTITY_NOT_FOUND);
        }

        List<RoleRepresentation> roleRepresentations = usersResource.get(userId).roles().realmLevel().listAll();
        List<RoleDto> roles = roleRepresentations.stream()
                .map(roleRepresentation -> {
                    RoleDto role = new RoleDto();
                    role.setName(roleRepresentation.getName());
                    role.setId(roleRepresentation.getId());
                    return role;
                })
                .collect(Collectors.toList());

        ClientDTO user = new ClientDTO();
        user.setId(userRepresentation.getId());
        user.setFirstName(userRepresentation.getFirstName());
        user.setUsername(userRepresentation.getUsername());
        user.setEmail(userRepresentation.getEmail());
        user.setLastName(userRepresentation.getLastName());
        user.setRoles(roles);
        user.setActive(userRepresentation.isEnabled());

        return user;
    }

}

