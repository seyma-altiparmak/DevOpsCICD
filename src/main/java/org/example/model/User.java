package org.example.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "person")
public class User {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "image")
    private String image;

    @Transient
    private MultipartFile imageFile;

    @Column(name = "link")
    private String LINK = "https://devops-swe304.s3.eu-north-1.amazonaws.com/";

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public  void setImageName(String image) {this.image = image;}
    public String getImageName() {
        return image;
    }
    public String getLINK() {
        return LINK + image;
    }

    public MultipartFile getImage() {
        return imageFile;
    }
    public void setImage(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
    public String getLink() {return LINK;}

    public String getImageLink(String fileName){
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty!");
        }
        setImageLink(fileName);
        return LINK + fileName + ".jpg";
    }
    public String getImageLinkJPG(String fileName){
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name cannot be null or empty!");
        }
        return LINK + fileName;
    }
    public void setImageLink(String fileName) {
        this.image = fileName;
    }


}