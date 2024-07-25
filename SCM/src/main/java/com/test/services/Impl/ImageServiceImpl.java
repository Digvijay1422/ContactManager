package com.test.services.Impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.test.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService{


    
    private Cloudinary cloudinary;

    
    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    @Override
    public String uploadImage(MultipartFile picture,String fileName) {
        
        //Code to upload image on server
        try {
            byte [] data = new byte[picture.getInputStream().available()];
            picture.getInputStream().read(data);
            cloudinary.uploader().upload(data,ObjectUtils.asMap(
                "public_id",fileName
            ));

        } catch (IOException e) {
            e.printStackTrace();
        } 

        return this.getUrlFromPublicId(fileName);
    }


    @Override
    public String getUrlFromPublicId(String publicId) {
        
        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
            .width(300)
            .height(300)
            .crop("fill") 
        )
        .generate(publicId);
    }
    


    
}
