package com.example.kidfinance;

public class ItemListSample {
    private String image_path;
    private String name;
    private Integer amount;
    private String description;


    public ItemListSample(String imageResource, String itemName) {
        this.image_path = imageResource;
        this.name = itemName;
        this.amount = 1;
        this.description = "";
    }

    public void addNumberOfItem() {
        if (amount < 100) {
            amount++;
        }
    }

    public void dropNumberOfItem() {
        if (amount > 1) {
            amount--;
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageResource() {
        return this.image_path;
    }

    public String getItemName() {
        return this.name;
    }

    public Integer getNumberOfItem() {
        return this.amount;
    }
}
