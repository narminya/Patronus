package com.demo.patronus.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    String uploadFile(MultipartFile file);
    String deleteFile(String fileName);
}
