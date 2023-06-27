package com.example.mils.demo.web.pushMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
// @RestController
public class MessageController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    // Mapped as /app/application
    @MessageMapping("/application")
    @SendTo("/all/messages")
    public Notification send(final Notification message) throws Exception {
        return message;
    }

    // Mapped as /app/private
    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Notification message) {
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }
}
