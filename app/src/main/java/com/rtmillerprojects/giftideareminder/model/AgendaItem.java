package com.rtmillerprojects.giftideareminder.model;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Ryan on 5/29/2016.
 */
public class AgendaItem {
    private long id;
    private Contact contact;
    private String notes;
    private Date date;
    private String title;
    private boolean recurring;
    private String recurrate;
    private Bitmap eventImage;

    public AgendaItem(Contact contact, String title, String notes, Date date){
        this.date = date;
        this.contact = contact;
        this.notes = notes;
        this.title = title;
    };
    public AgendaItem(){};

    public long getId(){ return id;}
    public void setId(long id){this.id = id;}

    public String getTitle(){ return title;}
    public void setTitle(String title){this.title = title;}

    public Date getDate(){ return date;}
    public void setDate(Date date){this.date = date;}

    public boolean getRecurring(){ return recurring;}
    public void setRecurring(boolean recurring){this.recurring = recurring;}

    public String getRecurRate(){ return recurrate;}
    public void setRecurRate(String recurrate){this.recurrate = recurrate;}

    public Bitmap getEventImage() {
        return eventImage;
    }
    public void setEventImage(Bitmap eventImage) {
        this.eventImage = eventImage;
    }
}
