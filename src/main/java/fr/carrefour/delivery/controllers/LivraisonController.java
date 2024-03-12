package fr.carrefour.delivery.controllers;

import fr.carrefour.delivery.dtos.DeliveryDto;
import fr.carrefour.delivery.entities.Delivery;
import fr.carrefour.delivery.exceptions.InvalidEntityException;
import fr.carrefour.delivery.handlers.ErrorCodes;
import fr.carrefour.delivery.services.interfaces.IDeliveryService;
import fr.carrefour.delivery.validators.DeliveryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
@Validated
public class LivraisonController {
    private IDeliveryService deliveryService;

    public LivraisonController(IDeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }
    

    @GetMapping
    public ResponseEntity<List<DeliveryDto>> getAllDeliveries() {
        List<DeliveryDto> deliveries = deliveryService.getAllDeliveries();
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<String> createDelivery(@RequestBody DeliveryDto deliveryDto) throws InvalidEntityException {
        List<String> errors = DeliveryValidator.validate(deliveryDto);
        if (!errors.isEmpty()) {
        throw new InvalidEntityException("invalid delivery values !", ErrorCodes.INVALID_ENTITY_EXCEPTION, errors);
        }
        return ResponseEntity.ok(deliveryService.saveDelevery(deliveryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
