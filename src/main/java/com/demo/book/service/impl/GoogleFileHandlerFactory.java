package com.demo.book.service.impl;

import com.demo.book.service.FileHandlerFactory;
import com.demo.book.service.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoogleFileHandlerFactory implements FileHandlerFactory {
    @Autowired
    private GoogleDriveFileUpload googleDriveFileUpload;
    @Override
    public FileUpload createFileUpload() {
        return googleDriveFileUpload;
    }
}
