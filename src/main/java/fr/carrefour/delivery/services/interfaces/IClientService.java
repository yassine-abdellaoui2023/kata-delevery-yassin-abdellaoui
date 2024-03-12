package fr.carrefour.delivery.services.interfaces;

import fr.carrefour.delivery.dtos.ClientDTO;
import fr.carrefour.delivery.exceptions.EntityAlreadyExistException;
import fr.carrefour.delivery.exceptions.EntityNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IClientService {

    String authAndGetToken(String username, String password);
    void register(ClientDTO clientDTO) throws EntityAlreadyExistException;

     List<ClientDTO> getAllClients();
    ClientDTO getClientById(String userId) throws EntityNotFoundException;


}
