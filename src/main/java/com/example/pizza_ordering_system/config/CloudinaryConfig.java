package com.example.pizza_ordering_system.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "YOUR_CLOUD_NAME",
                "api_key", "YOUR KEY",
                "api_secret", "YOUR_SECRET KEY"
        ));
    }
}
