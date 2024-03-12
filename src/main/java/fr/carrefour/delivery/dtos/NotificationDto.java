package fr.carrefour.delivery.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NotificationDto {


    private Long id;
    private String message;
    private ClientDTO sender;


}
