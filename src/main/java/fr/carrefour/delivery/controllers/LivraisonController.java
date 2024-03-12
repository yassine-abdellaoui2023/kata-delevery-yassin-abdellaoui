package fr.carrefour.delivery.controllers;

import fr.carrefour.delivery.dtos.DeliveryDto;
import fr.carrefour.delivery.exceptions.InvalidEntityException;
import fr.carrefour.delivery.handlers.ErrorCodes;
import fr.carrefour.delivery.services.interfaces.IDeliveryService;
import fr.carrefour.delivery.validators.DeliveryValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
@Validated
@Tag(name = "Delivery Management", description = "Endpoints for managing deliveries")
public class LivraisonController {

    private final IDeliveryService deliveryService;

    public LivraisonController(IDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @Operation(summary = "Get all deliveries")
    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    @GetMapping
    public ResponseEntity<List<DeliveryDto>> getAllDeliveries() {
        List<DeliveryDto> deliveries = deliveryService.getAllDeliveries();
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @Operation(summary = "Create a new delivery")

    @ApiResponse(responseCode = "200", description = "Delivery created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<String> createDelivery(@RequestBody DeliveryDto deliveryDto) throws InvalidEntityException {
        List<String> errors = DeliveryValidator.validate(deliveryDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("Invalid delivery values!", ErrorCodes.INVALID_ENTITY_EXCEPTION, errors);
        }
        return ResponseEntity.ok(deliveryService.saveDelevery(deliveryDto));
    }

    @Operation(summary = "Delete a delivery by ID")
    @ApiResponse(responseCode = "204", description = "Delivery deleted successfully")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
