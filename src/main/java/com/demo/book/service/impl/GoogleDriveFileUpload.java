package com.demo.book.service.impl;

import com.demo.book.service.FileUpload;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component("googleDriveFileUpload")
public class GoogleDriveFileUpload implements FileUpload {
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return "null";
    }
}
