package org.example.s3.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service

public class S3Service {
    private AmazonS3 s3Client;

    @Autowired
    public void S3UploadService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        System.out.println("Dosya yükleniyor: " + file.getOriginalFilename());
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest("devops-swe304", fileName, file.getInputStream(), new ObjectMetadata()));
            return fileName;
        } else {
            throw new IllegalArgumentException("Dosya boş olamaz.");
        }
    }


    public String getImageLink(String fileName) {
        return s3Client.getUrl("devops-swe304", fileName).toString();
    }
}