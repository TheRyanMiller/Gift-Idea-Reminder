package com.rtmillerprojects.giftideareminder.model;

import android.graphics.Bitmap;

/**
 * Created by Ryan on 5/29/2016.
 */
public class Gift {
    private String name;
    private String url;
    private String photoLocation;
    private String notes;
    private long id;

    public Gift(){};
    public Gift(String name, String url, String photoLocation, String notes) {
        this.name = name;
        this.url = url;
        this.photoLocation = photoLocation;
        this.notes = notes;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl(){return url;}
    public void setUrl(String url){this.url = url;}

    public String getPhotoLocation(){return photoLocation;}
    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public String getNotes(){return notes;}
    public void setNotes(String notes){this.notes = notes;}

}
