package com.demo.patronus.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public interface StorageService {


    String uploadFile(MultipartFile file);

    String uploadFile(File file);
}
