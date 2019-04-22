package com.example.kidfinance;

public class AchievementModel {
    private int achievement_logo;
    private String achievement_title;
    private String achievement_description;
    private float achievement_amount;
    private int achievement_success;

    public AchievementModel(int logo, String title, String description, float amount, int success) {
        this.achievement_logo = logo;
        this.achievement_title = title;
        this.achievement_description = description;
        this.achievement_amount = amount;
        this.achievement_success = success;
    }

    public int getAchievementLogo() {
        return this.achievement_logo;
    }

    public String getAchievementTitle() {
        return this.achievement_title;
    }

    public String getAchievementDescription() {
        return this.achievement_description;
    }

    public float getAchievementAmount() {
        return this.achievement_amount;
    }

    public int getAchievementSuccess() {
        return this.achievement_success;
    }

    public void setAchievementSuccess(int success) {
        this.achievement_success = success;
    }
}