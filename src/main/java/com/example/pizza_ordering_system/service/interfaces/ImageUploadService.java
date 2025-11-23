package com.example.pizza_ordering_system.service.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {
    String uploadImage(MultipartFile file) throws IOException;
}
