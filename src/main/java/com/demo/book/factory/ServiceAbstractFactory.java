package com.demo.book.factory;

import org.springframework.stereotype.Component;

@Component
public abstract class ServiceAbstractFactory {
        public abstract IBook createIBook();
        public abstract IUser createIUser();
}
