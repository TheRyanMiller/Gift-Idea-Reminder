package com.rtmillerprojects.giftideareminder.model;

import android.graphics.Bitmap;

/**
 * Created by Ryan on 5/29/2016.
 */
public class Contact {
    private long id;
    private String name;
    private Bitmap profilePhoto;
    private String relationship;

    public Contact(String name, String relationship, Bitmap profilePhoto) {
        this.name = name;
        this.relationship = relationship;
        this.profilePhoto = profilePhoto;
    }
    public Contact(){} //Empty constructor

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public Bitmap getProfilePhoto() {
        return profilePhoto;
    }
    public void setProfilePhoto(Bitmap profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }
    public void setRelationship(String name) {
        this.relationship = name;
    }
}