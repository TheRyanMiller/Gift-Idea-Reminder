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
    public AgendaItem(Contact contact, String title, String notes, Date date){
        this.date = date;
        this.contact = contact;
        this.notes = notes;
        this.title = title;
    };
}
