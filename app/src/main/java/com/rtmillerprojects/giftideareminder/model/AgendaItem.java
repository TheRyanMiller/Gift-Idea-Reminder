package com.rtmillerprojects.giftideareminder.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Ryan on 5/29/2016.
 */
public class AgendaItem implements Parcelable {
    private long id;
    private String notes;
    private Date date;
    private String title;
    private boolean recurring;
    private String recurrate;
    private Bitmap eventImage;

    public AgendaItem(String title, String notes, Date date){
        this.date = date;
        this.notes = notes;
        this.title = title;
    };
    public AgendaItem(){};

    protected AgendaItem(Parcel in) {
        id = in.readLong();
        notes = in.readString();
        title = in.readString();
        recurring = in.readByte() != 0;
        recurrate = in.readString();
        eventImage = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<AgendaItem> CREATOR = new Creator<AgendaItem>() {
        @Override
        public AgendaItem createFromParcel(Parcel in) {
            return new AgendaItem(in);
        }

        @Override
        public AgendaItem[] newArray(int size) {
            return new AgendaItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(notes);
        dest.writeString(title);
        dest.writeByte((byte) (recurring ? 1 : 0));
        dest.writeString(recurrate);
        dest.writeParcelable(eventImage, flags);
    }
}
