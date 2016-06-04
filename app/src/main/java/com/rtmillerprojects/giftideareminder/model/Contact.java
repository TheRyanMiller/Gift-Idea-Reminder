package com.rtmillerprojects.giftideareminder.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Ryan on 5/29/2016.
 */
public class Contact {
    private long id;
    private String name;
    private String username;
    private Bitmap profilePhoto;
    private String relationship;

    public Contact(String name, String username, Bitmap profilePhoto) {
        this.name = name;
        this.username = username;
        this.profilePhoto = profilePhoto;
    }
    public Contact(){} //Empty constructor

    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public Bitmap getProfilePhoto() {
        return profilePhoto;
    }
    public void setProfilePhotoUrl(Bitmap profilePhotoUrl) {
        this.profilePhoto = profilePhotoUrl;
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

    public String getRelationship() {
        return relationship;
    }
    public void setRelationship(String name) {
        this.relationship = name;
    }
}