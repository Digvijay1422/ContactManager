package com.test.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class AppConfig {
    
    @Value("${Cloudernary.cloud.name}")
    private String cloudName;
    @Value("${Cloudernary.api.key}")
    private String apiKey;
    @Value("${Cloudernary.api.secret}")
    private String apiSecret;
    @Bean
    public Cloudinary cloudinary()
    {
        return new Cloudinary(
            ObjectUtils.asMap(
                "cloud_name" , cloudName,
                "api_key" , apiKey,
                "api_secret" , apiSecret
    
            )
        );
    }
}
