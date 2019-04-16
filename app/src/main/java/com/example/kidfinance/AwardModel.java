package com.example.kidfinance;

class AwardModel {
    private int image;
    private String name;
    private String description;

    AwardModel(int image, String name, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }

    int getImage() {
        return image;
    }

    void setImage(int image) {
        this.image = image;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

}
