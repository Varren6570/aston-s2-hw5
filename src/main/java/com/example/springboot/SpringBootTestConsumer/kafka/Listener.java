package com.example.springboot.SpringBootTestConsumer.kafka;

import com.example.springboot.SpringBootTestConsumer.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
/**
 * Kafka‑слушатель сообщений из топика {@code notification-topic}.
 * Обрабатывает события создания и удаления аккаунтов.
 */
@Component
public class Listener {

    EmailSenderService senderService;

    @Autowired
    public Listener(EmailSenderService senderService) {
        this.senderService = senderService;
    }
    /**
     * Обрабатывает входящее Kafka‑сообщение.
     *
     * @param type  тип события (CREATE или DELETE), передаётся в заголовке {@code eventType}
     * @param email email‑адрес, передаётся как тело сообщения
     */
    @KafkaListener(topics = "notification-topic", groupId = "group1")
    void listener(@Header("eventType") String type,
                  @Payload String email) {
        switch (type) {
            case "CREATE" -> {
                System.out.println("Received message in group1: " + email);
                senderService.sendCreateEmail(email);
            }
            case "DELETE" -> {
                System.out.println("Received message in group1: " + email);
                senderService.sendDeleteEmail(email);
            }
        }
    }
}
