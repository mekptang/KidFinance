package com.example.kidfinance;

class AwardModel {
    private int image;
    private String name;
    private String description;

    //new code by roy
    private String image_path;
    private String amount;

    //new code by roy
    AwardModel(String image, String name, String amount, String description) {
        this.name = name;
        this.image_path = image;
        this.amount = amount;
        this.description = description;
        this.image = -1;
    }

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

    //new code by roy
    String getImage_path(){
        return image_path;
    }

    String getAmount(){
        return amount;
    }

}
