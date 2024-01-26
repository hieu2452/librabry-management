package com.demo.book.event.notification;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener implements ApplicationListener<NotificationEvent> {
    @Async
    @Override
    public void onApplicationEvent(NotificationEvent event) {
        System.out.println("Notification :" + event.getMessage());
    }
}
