package com.example.mils.demo.web.pushMessage;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class MilestoneListener {

    @EventListener // Springで用意されているアノテーションを付与
    public String onEvent(MilestoneEvent milestoneEvent) { // 引数にイベントのオブジェクトを設定する
        System.out.println("Received spring custom event - " + milestoneEvent.getMessage());
        return milestoneEvent.getMessage();
    }
}
