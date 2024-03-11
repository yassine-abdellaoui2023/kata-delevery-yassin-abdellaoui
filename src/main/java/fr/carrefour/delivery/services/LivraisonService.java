package fr.carrefour.delivery.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.carrefour.delivery.entities.Livraison;
import fr.carrefour.delivery.repositories.LivraisonRepository;

@Service
public class LivraisonService {
    @Autowired
    private LivraisonRepository livraisonRepository;

    public List<Livraison> getAllLivraisons() {
        return livraisonRepository.findAll();
    }

    public Livraison getLivraisonById(int id) {
        return livraisonRepository.findById(id).orElse(null);
    }

    public Livraison saveLivraison(Livraison livraison) {
        return livraisonRepository.save(livraison);
    }

    public void deleteLivraison(int id) {
        livraisonRepository.deleteById(id);
    }
}
