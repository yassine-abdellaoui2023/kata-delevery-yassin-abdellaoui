package fr.carrefour.delivery.entities;

import java.util.Date;

import fr.carrefour.delivery.entities.enums.DeliveryMode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_delivery")
    private Long idDelivery;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_mode", nullable = false)
    private DeliveryMode deliveryMode;

    @Column(name = "delivery_date", nullable = false)
    private Date deliveryDate;

    @Column(name = "client_id", nullable = false)
    private String clientId;
}