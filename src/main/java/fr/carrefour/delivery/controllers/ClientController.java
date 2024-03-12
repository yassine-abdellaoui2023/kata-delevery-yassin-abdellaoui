package fr.carrefour.delivery.controllers;

import fr.carrefour.delivery.dtos.ClientDTO;
import fr.carrefour.delivery.exceptions.EntityAlreadyExistException;
import fr.carrefour.delivery.exceptions.EntityNotFoundException;
import fr.carrefour.delivery.exceptions.InvalidEntityException;
import fr.carrefour.delivery.handlers.ErrorCodes;
import fr.carrefour.delivery.services.impl.ClientService;
import fr.carrefour.delivery.services.interfaces.IClientService;
import fr.carrefour.delivery.validators.ClientValidator;
import fr.carrefour.delivery.validators.DeliveryValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@Validated
public class ClientController {

    private IClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping(path = "/auth/{username}/{password}")
    public ResponseEntity<String> authentication(@PathVariable String username, @PathVariable String password) {
    String token = clientService.authAndGetToken(username,password);
     return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody ClientDTO clientDTO) throws EntityAlreadyExistException, InvalidEntityException {
        List<String> errors = ClientValidator.validate(clientDTO);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("invalid delivery values !", ErrorCodes.INVALID_ENTITY_EXCEPTION, errors);
        }
        clientService.register(clientDTO);
        return ResponseEntity.ok().body("Register successful");
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable String userId) throws EntityNotFoundException {
        return ResponseEntity.ok(clientService.getClientById(userId));
    }
}