package com.demo.patronus.services.impl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.demo.patronus.exception.ThumbnailUploaderException;
import com.demo.patronus.properties.AwsProperties;
import com.demo.patronus.services.StorageService;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final S3Template s3Template;
    private final AwsProperties awsProperties;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            return uploadFile(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            String message = String.format("Unable to upload MultipartFile %s", file.getOriginalFilename());
            throw new ThumbnailUploaderException(message, e);
        }
    }

    @Override
    public String uploadFile(File file) {
        try {
            return uploadFile(file.getName(), new FileInputStream(file));
        } catch (IOException e) {
            String message = String.format("Unable to upload File %s", file.getName());
            throw new ThumbnailUploaderException(message, e);
        }
    }

    private String uploadFile(String key, InputStream inputStream) throws IOException {
        String bucketName = awsProperties.getS3().getBucketName();
        s3Template.upload(bucketName, key, inputStream);
        String s3FileUrl = String.format("%s/%s/%s", awsProperties.getEndpoint(), bucketName, key);
        log.info("File '{}' uploaded successfully in S3! URL: {}", key, s3FileUrl);
        return s3FileUrl;
    }
}