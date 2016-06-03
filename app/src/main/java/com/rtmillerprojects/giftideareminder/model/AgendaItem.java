package com.rtmillerprojects.giftideareminder.model;

import java.util.Date;

/**
 * Created by Ryan on 5/29/2016.
 */
public class AgendaItem {
    private Contact contact;
    private String notes;
    private Date date;
    private String title;
    private boolean recurring;
    private String recurringRate;

    public AgendaItem(Contact contact, String title, String notes, Date date){
        this.date = date;
        this.contact = contact;
        this.notes = notes;
        this.title = title;
    };

    public String getTitle(){ return title;}
    public void setTitle(String title){this.title = title;}

    public Date getDate(){ return date;}
    public void setDate(Date date){this.date = date;}

    public boolean getRecurring(){ return recurring;}
    public void setRecurring(boolean recurring){this.recurring = recurring;}
}
