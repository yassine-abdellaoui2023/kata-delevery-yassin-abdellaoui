package fr.carrefour.delivery.controllers;

import fr.carrefour.delivery.dtos.ClientDTO;
import fr.carrefour.delivery.exceptions.EntityAlreadyExistException;
import fr.carrefour.delivery.exceptions.EntityNotFoundException;
import fr.carrefour.delivery.exceptions.InvalidEntityException;
import fr.carrefour.delivery.handlers.ErrorCodes;
import fr.carrefour.delivery.services.interfaces.IClientService;
import fr.carrefour.delivery.validators.ClientValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@Validated
@Tag(name = "Client Management", description = "Endpoints for managing clients")
public class ClientController {

    private final IClientService clientService;

    public ClientController(IClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Authenticate user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping(path = "/auth/{username}/{password}")
    public ResponseEntity<String> authentication(
            @Parameter(description = "Username", required = true, example = "user123")
            @PathVariable String username,
            @Parameter(description = "Password", required = true, example = "password123")
            @PathVariable String password) {
        String token = clientService.authAndGetToken(username, password);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Register a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = ErrorCodes.class)))
    })
    @PostMapping(path = "/register")
    public ResponseEntity<String> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Client information", required = true,
                    content = @Content(schema = @Schema(implementation = ClientDTO.class)))
            @RequestBody ClientDTO clientDTO) throws EntityAlreadyExistException, InvalidEntityException {
        List<String> errors = ClientValidator.validate(clientDTO);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("invalid delivery values !", ErrorCodes.INVALID_ENTITY_EXCEPTION, errors);
        }
        clientService.register(clientDTO);
        return ResponseEntity.ok().body("Register successful");
    }
    @Operation(summary = "Get all clients")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = @Content(schema = @Schema(implementation = ClientDTO.class)))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @Operation(summary = "Get client by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(schema = @Schema(implementation = ClientDTO.class))),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<ClientDTO> getClientById(
            @Parameter(description = "User ID", required = true, example = "123")
            @PathVariable String userId) throws EntityNotFoundException {
        return ResponseEntity.ok(clientService.getClientById(userId));
    }
}