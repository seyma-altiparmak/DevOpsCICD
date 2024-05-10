package org.example.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class S3UploadImage {

    private static final Logger LOGGER = Logger.getLogger(S3UploadImage.class.getName());
    private static final String BUCKET_NAME = "devops-swe304";
    private static final Region REGION = Region.EU_NORTH_1;

    private final S3Client s3Client;

    public S3UploadImage() {
        AwsCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create();
        s3Client = S3Client.builder()
                .region(REGION)
                .credentialsProvider(credentialsProvider)
                .build();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if (!isValidFile(file)) {
            throw new IllegalArgumentException("Invalid file. Only non-empty JPG images are allowed!");
        }

        String fileName = generateFileName(file);
        PutObjectResponse response = s3Client.putObject(PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(fileName)
                        .contentType("image/jpg")
                        .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        if (response.sdkHttpResponse().isSuccessful()) {
            LOGGER.info("File uploaded successfully: " + fileName);
            return "File uploaded : " + fileName;
        } else {
            LOGGER.severe("Failed to upload the file to S3: " + response.sdkHttpResponse().statusCode());
            throw new RuntimeException("Failed to upload the file to S3!");
        }
    }

    private boolean isValidFile(MultipartFile file) {
        return file!= null &&!file.isEmpty() && file.getContentType()!= null && file.getContentType().equals("image/jpg");
    }

    private String generateFileName(MultipartFile file) {
        String originalFileName = Objects.requireNonNull(file.getOriginalFilename()).replaceAll(" ", "_");
        return UUID.randomUUID() + "_" + (originalFileName.endsWith(".jpg")? originalFileName : originalFileName + ".jpg");
    }

    public String getImageLink(String fileName){
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty!");
        }
        LOGGER.info("Generating image link for file: " + fileName);
        String url = "https://devops-swe304.s3.eu-north-1.amazonaws.com/" + fileName ;
        return url;
    }
}