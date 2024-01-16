package com.demo.book.config;

import com.demo.book.service.FileHandlerFactory;
import com.demo.book.service.impl.CloudinaryFileHandlerFactory;
import com.demo.book.service.impl.GoogleFileHandlerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
    @Value("${storage-type}")
    private String storageType;

    @Bean
    public FileHandlerFactory fileHandlerFactory() {
        if ("google-drive".equalsIgnoreCase(storageType)) {
            return new GoogleFileHandlerFactory();
        } else {
            return new CloudinaryFileHandlerFactory();
        }
    }
}
