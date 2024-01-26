package com.demo.book.event.notification;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

public class NotificationEvent extends ApplicationEvent {
    private final Logger logger = LoggerFactory.getLogger(NotificationEvent.class);
    @Getter
    private String message;
    public NotificationEvent(Object source,String message) {
        super(source);
        this.message = message;
    }

}
