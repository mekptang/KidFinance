package com.example.kidfinance;

public class AccountModel {
    private String account_name;
    private int account_gender;
    private int account_age;
    private int account_icon;

    public AccountModel(String name, int gender, int age, int icon) {
        this.account_name = name;
        this.account_gender = gender;
        this.account_age = age;
        this.account_icon = icon;
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

    public int getAccountIconID() {
        return this.account_icon;
    }
}
