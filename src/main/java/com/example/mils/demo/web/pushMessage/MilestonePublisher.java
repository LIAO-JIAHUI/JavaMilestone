package com.example.mils.demo.web.pushMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
public class MilestonePublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishMilestoneEvent(final String message) {
        System.out.println("Publishing custom event. ");
        MilestoneEvent milestoneEvent = new MilestoneEvent(this, message);
        applicationEventPublisher.publishEvent(milestoneEvent);
    }
}
