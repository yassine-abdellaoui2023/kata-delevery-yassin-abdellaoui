package fr.carrefour.delivery.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.carrefour.delivery.DTO.ClientDTO;
import fr.carrefour.delivery.kafka.JsonKafkaProducer;
import fr.carrefour.delivery.kafka.KafkaProducer;
import fr.carrefour.delivery.utils.Constants;

@RestController
@RequestMapping(Constants.KAFKA_ENDPOINT)
public class KafkaMessageController {

    private KafkaProducer kafkaProducer;
    private JsonKafkaProducer jsonkafkaProducer;

    public KafkaMessageController(KafkaProducer kafkaProducer,JsonKafkaProducer jsonkafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        this.jsonkafkaProducer = jsonkafkaProducer;
    }

    @GetMapping("/read")
    public ResponseEntity<String> publish(@RequestParam("message") String message){
        kafkaProducer.sendMessage(message);
        return ResponseEntity.ok("Message sent to the topic");
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody ClientDTO client){
        jsonkafkaProducer.sendMessage(client);
        return ResponseEntity.ok("Json message sent to kafka topic");
    }
}