package com.test.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

     String uploadImage(MultipartFile picture ,String fileName) ;
     String getUrlFromPublicId(String publicId);
    
}
