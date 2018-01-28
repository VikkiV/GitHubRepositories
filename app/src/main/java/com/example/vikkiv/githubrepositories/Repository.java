package com.example.vikkiv.githubrepositories;

public class Repository {
    private String imageURL;
    private String name;
    private String description;

    public  Repository (String imageURL, String name, String description) {
        this.imageURL = imageURL;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getimageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }
}
