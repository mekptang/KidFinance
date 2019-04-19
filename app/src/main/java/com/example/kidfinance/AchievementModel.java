package com.example.kidfinance;

public class AchievementModel {
    private int achievement_logo;
    private String achievement_title;
    private String achievement_description;
    private int achievement_success;

    public AchievementModel(int logo, String title, String description, int success) {
        this.achievement_logo = logo;
        this.achievement_title = title;
        this.achievement_description = description;
        this.achievement_success = success;
    }

    public int getAchievementLogo(){
        return this.achievement_logo;
    }

    public String getAchievementTitle(){
        return this.achievement_title;
    }

    public String getAchievementDescription(){
        return this.achievement_description;
    }

    public int getAchievementSuccess(){
        return this.achievement_success;
    }
}