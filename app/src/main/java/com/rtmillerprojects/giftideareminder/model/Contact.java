package com.rtmillerprojects.giftideareminder.model;

/**
 * Created by Ryan on 5/29/2016.
 */
public class Contact {
    private String name;
    private String username;
    private String profilePhotoUrl;

    public Contact(String name, String username, String profilePhotoUrl) {
        this.name = name;
        this.username = username;
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}