package com.example.login;

public class User {

    String name,phoneNUmber,emailId,username,imageUrl;

    public String getName() {
        return name;
    }

    public String getPhoneNUmber() {
        return phoneNUmber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNUmber(String phoneNUmber) {
        this.phoneNUmber = phoneNUmber;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
