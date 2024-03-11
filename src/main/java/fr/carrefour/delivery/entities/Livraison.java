package fr.carrefour.delivery.entities;

import java.util.Date;

import fr.carrefour.delivery.enums.ModeDeLivraison;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "livraison")
public class Livraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idlivraison")
    private Integer idlivraison;

    @Enumerated(EnumType.STRING)
    @Column(name = "modeDeLivraison", nullable = false)
    private ModeDeLivraison modeDeLivraison;

    @Column(name = "dateDeLivraison", nullable = false)
    private Date dateDeLivraison;

    @ManyToOne
    @JoinColumn(name = "Client_idClient", referencedColumnName = "idClient")
    private Client client;
}