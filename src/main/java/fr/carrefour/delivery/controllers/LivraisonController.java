package fr.carrefour.delivery.controllers;

import fr.carrefour.delivery.exception.ErrorCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import fr.carrefour.delivery.DTO.LivraisonDTO;
import fr.carrefour.delivery.entities.Livraison;
import fr.carrefour.delivery.exception.InvalidEntityException;
import fr.carrefour.delivery.services.LivraisonService;
import fr.carrefour.delivery.utils.Constants;
import fr.carrefour.delivery.validators.LivraisonValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import java.util.List;

@RestController
@RequestMapping(Constants.LIVRAISON_ENDPOINT)
@Validated
public class LivraisonController {

    private final Logger log = LoggerFactory.getLogger(Livraison.class);

    private LivraisonService livraisonService;

    public LivraisonController(LivraisonService livraisonService){
        this.livraisonService = livraisonService;
    }
    

    @GetMapping
    public ResponseEntity<List<Livraison>> getAllLivraisons() {
        List<Livraison> livraisons = livraisonService.getAllLivraisons();
        return new ResponseEntity<>(livraisons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livraison> getLivraisonById(@PathVariable @Min(1) int id) {
        Livraison livraison = livraisonService.getLivraisonById(id);
        return new ResponseEntity<>(livraison, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Livraison> addLivraison(@Valid @RequestBody Livraison livraison) {
        log.debug("REST request to save Abonne : {}", livraison);
        LivraisonDTO livraisonDTO = LivraisonDTO.fromEntity(livraison);
        List<String> errors = LivraisonValidator.validate(livraisonDTO);
        if (!errors.isEmpty()) {
        log.error("Livraison invalide {}", livraisonDTO);
        throw new InvalidEntityException("Livraison invalide", ErrorCodes.LIVRAISON_NOT_VALID, errors);
        }
        Livraison savedLivraison = livraisonService.saveLivraison(livraison);
        return new ResponseEntity<>(savedLivraison, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivraison(@PathVariable @Min(1) Integer id) {
        livraisonService.deleteLivraison(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
