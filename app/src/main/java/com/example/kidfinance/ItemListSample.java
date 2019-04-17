package com.example.kidfinance;

public class ItemListSample {
    private String imageResource;
    private String itemName;
    private Integer numberOfItem;


    public ItemListSample(String imageResource, String itemName) {
        this.imageResource = imageResource;
        this.itemName = itemName;
        this.numberOfItem = 1;
    }

    public void addNumberOfItem(){
        if(numberOfItem < 100){
            numberOfItem++;
        }
    }

    public void dropNumberOfItem(){
        if(numberOfItem > 1){
            numberOfItem--;
        }
    }

    public String getImageResource(){
        return this.imageResource;
    }
    public String getItemName(){
        return this.itemName;
    }
    public Integer getNumberOfItem() {
        return this.numberOfItem;
    }
}
