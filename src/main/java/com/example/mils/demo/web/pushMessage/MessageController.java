package com.example.mils.demo.web.pushMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    /**
     * send to all
     * 
     * @param message
     * @throws Exception
     */
    // Mapped as /app/application
    @MessageMapping("/application")
    public void send(final Notification message) throws Exception {
        simpMessagingTemplate.convertAndSend("/all/messages", message);
        // return message;
    }

    /**
     * send to user
     * 
     * @param message
     */
    // Mapped as /app/private
    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Notification message) {
        for (String to : message.getTo()) {
            if (!to.equals(message.getFrom()))
                simpMessagingTemplate.convertAndSendToUser(to, "/specific", message);
        }
    }
}
