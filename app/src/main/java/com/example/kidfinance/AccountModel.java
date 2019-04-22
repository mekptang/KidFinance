package com.example.kidfinance;

public class AccountModel {
    private String account_name;
    private int account_gender;
    private int account_age;
    private String account_icon_uri;

    public AccountModel(String name, int gender, int age, String icon_uri) {
        this.account_name = name;
        this.account_gender = gender;
        this.account_age = age;
        this.account_icon_uri = icon_uri;
    }

    public String getAccountName() {
        return this.account_name;
    }

    public int getAccountGender() {
        return this.account_gender;
    }

    public int getAccountAge() {
        return this.account_age;
    }

    public String getAccountIconPath() {
        return this.account_icon_uri;
    }
}