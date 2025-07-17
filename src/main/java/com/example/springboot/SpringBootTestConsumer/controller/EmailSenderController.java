package com.example.springboot.SpringBootTestConsumer.controller;

import com.example.springboot.SpringBootTestConsumer.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST‑контроллер для отправки уведомлений на электронную почту.
 *
 * <p>URL: {@code /api/sendMail}
 */
@RestController
@RequestMapping("/api/sendMail")
public class EmailSenderController {

  private final EmailSenderService senderService;

  @Autowired
  public EmailSenderController(EmailSenderService senderService) {
    this.senderService = senderService;
  }

  /**
   * Отправляет письмо о создании аккаунта.
   *
   * @param email адрес получателя
   * @return сообщение об успешной отправке
   */
  @PostMapping("/create")
  public ResponseEntity<String> sendCreateEmail(@RequestBody String email) {
    senderService.sendCreateEmail(email);
    return new ResponseEntity<>("Письмо о создании аккаунта отправлено", HttpStatus.OK);
  }

  /**
   * Отправляет письмо об удалении аккаунта.
   *
   * @param email адрес получателя
   * @return сообщение об успешной отправке
   */
  @PostMapping("/delete")
  public ResponseEntity<String> sendDeleteEmail(@RequestBody String email) {
    senderService.sendDeleteEmail(email);
    return new ResponseEntity<>("Письмо об удалении аккаунта отправлено", HttpStatus.OK);
  }
}
//
//