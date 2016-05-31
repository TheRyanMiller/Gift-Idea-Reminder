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
    private String name;
    private String username;
    private Bitmap profilePhoto;

    public Contact(String name, String username, Bitmap profilePhoto) {
        this.name = name;
        this.username = username;
        this.profilePhoto = profilePhoto;
    }

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
}