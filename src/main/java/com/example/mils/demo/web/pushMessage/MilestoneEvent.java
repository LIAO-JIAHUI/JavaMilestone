package com.example.mils.demo.web.pushMessage;

import org.springframework.context.ApplicationEvent;

public class MilestoneEvent extends ApplicationEvent {
    private String message;

    public MilestoneEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
