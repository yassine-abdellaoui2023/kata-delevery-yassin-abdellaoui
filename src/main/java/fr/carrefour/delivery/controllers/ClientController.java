package fr.carrefour.delivery.controllers;

import fr.carrefour.delivery.exception.ErrorCodes;
import fr.carrefour.delivery.validators.ClientValidator;
import fr.carrefour.delivery.validators.LivraisonValidator;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

import fr.carrefour.delivery.DTO.ClientDTO;
import fr.carrefour.delivery.DTO.LivraisonDTO;
import fr.carrefour.delivery.entities.Client;
import fr.carrefour.delivery.entities.Livraison;
import fr.carrefour.delivery.exception.InvalidEntityException;
import fr.carrefour.delivery.services.ClientService;
import fr.carrefour.delivery.services.LivraisonService;
import fr.carrefour.delivery.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Constants.CLIENT_ENDPOINT)
@Validated
public class ClientController {

    

    private final Logger log = LoggerFactory.getLogger(Client.class);
    
    private ClientService clientService;
    private LivraisonService livraisonService;

    public ClientController(ClientService clientService,LivraisonService livraisonService){
        this.clientService = clientService;
        this.livraisonService = livraisonService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clientDTOs = clientService.getAllClients().stream().map(ClientDTO::fromEntity ).collect(Collectors.toList());
        return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable @Min(1) Integer id) {
        ClientDTO clientDTO = ClientDTO.fromEntity(clientService.getClientById(id));
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @PostMapping("/chooseNiche")
    public ResponseEntity<LivraisonDTO>  chooseNiche(@RequestBody LivraisonDTO livraisonDTO) {
        
        List<String> errors = LivraisonValidator.validate(livraisonDTO);
        if (!errors.isEmpty()) {
        log.error("Livraison invalide {}", livraisonDTO);
        throw new InvalidEntityException("Livraison invalide", ErrorCodes.LIVRAISON_NOT_VALID, errors);
        }

        livraisonDTO.setIdClient(livraisonDTO.getIdClient());
        Livraison livraison = LivraisonDTO.toEntity(livraisonDTO);
        Client clientToSave = clientService.getClientById(livraisonDTO.getIdClient());
        livraison.setClient(clientToSave);
        livraisonService.saveLivraison(livraison);
        return new ResponseEntity<>(livraisonDTO, HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> addClient(@Valid @RequestBody Client client) {
        
        ClientDTO clientDTO = ClientDTO.fromEntity(client);
        log.debug("REST request to save client : {}", clientDTO);
        List<String> errors = ClientValidator.validate(clientDTO);
        if (!errors.isEmpty()) {
        log.error("Client invalide {}", clientDTO);
        throw new InvalidEntityException("Client invalide", ErrorCodes.CLIENT_NOT_VALID, errors);
        }
        clientService.saveClient(client);
        return new ResponseEntity<>(clientDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable @Min(1) Integer id) {
        clientService.deleteClient(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}