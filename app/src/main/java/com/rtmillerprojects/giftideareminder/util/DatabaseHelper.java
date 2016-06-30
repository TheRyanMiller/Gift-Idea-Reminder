package com.rtmillerprojects.giftideareminder.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ParseException;
import android.util.Log;

import com.rtmillerprojects.giftideareminder.model.AgendaItem;
import com.rtmillerprojects.giftideareminder.model.Contact;
import com.rtmillerprojects.giftideareminder.model.Gift;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ryan on 5/31/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 5;

    //Table Defs
    public static final String DATABASE_NAME = "giftymcgiftface.db";
    public static final String TABLE_AGENDA_ITEMS = "agenda_items";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_GIFTS = "gifts";
    public static final String TABLE_CONTACTS_GIFTS = "contacts_gifts";
    public static final String TABLE_CONTACTS_EVENTS = "contacts_events";
    public static final String TABLE_EVENTS_GIFTS = "events_gifts";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String CONTACTS_ID = "contacts_id";
    private static final String GIFTS_ID = "gifts_id";
    private static final String EVENTS_ID = "events_id";
    private static final String KEY_CREATED_AT = "created_at";

    // Agenda items columns
    private static final String EVENT_DATE = "date";
    private static final String EVENT_TITLE = "title";
    private static final String EVENT_RECURRING = "recurring";
    private static final String EVENT_RECURRATE = "recurrate";
    private static final String EVENT_IMAGE = "image";

    // Contacts columns
    private static final String CONTACT_NAME = "name";
    private static final String CONTACT_IMAGE = "image";
    private static final String CONTACT_RELATIONSHIP = "relationship";
    // Gifts columns
    private static final String GIFT_NAME = "name";
    private static final String GIFT_URL = "url";
    private static final String GIFT_PHOTO_LOCATION = "photo_location";
    private static final String GIFT_NOTES = "notes";


    private static final String CREATE_TABLE_AGENDA_ITEMS = "CREATE TABLE " +
            TABLE_AGENDA_ITEMS + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            EVENT_TITLE + " TEXT," +
            EVENT_DATE + " DATE," +
            EVENT_RECURRING + " BOOLEAN," +
            EVENT_RECURRATE + " TEXT," +
            EVENT_IMAGE + " BLOB," +
            KEY_CREATED_AT + " DATETIME" + ")";

    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            CONTACT_NAME + " TEXT," +
            CONTACT_RELATIONSHIP + " TEXT," +
            CONTACT_IMAGE + " BLOB," +
            KEY_CREATED_AT + " DATETIME" + ")";

    private static final String CREATE_TABLE_GIFTS = "CREATE TABLE " + TABLE_GIFTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            GIFT_NAME + " TEXT," +
            GIFT_URL + " TEXT," +
            GIFT_PHOTO_LOCATION + " TEXT," +
            KEY_CREATED_AT + " DATETIME" + ")";

    //Create Associative tables
    private static final String CREATE_TABLE_CONTACTS_GIFTS = "CREATE TABLE " + TABLE_CONTACTS_GIFTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            CONTACTS_ID + " INTEGER," +
            GIFTS_ID + " INTEGER," +
            KEY_CREATED_AT + " DATETIME" + ")";

    private static final String CREATE_TABLE_CONTACTS_EVENTS = "CREATE TABLE " + TABLE_CONTACTS_EVENTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            CONTACTS_ID + " INTEGER," +
            EVENTS_ID + " INTEGER," +
            KEY_CREATED_AT + " DATETIME" + ")";

    private static final String CREATE_TABLE_EVENTS_GIFTS = "CREATE TABLE " + TABLE_EVENTS_GIFTS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY," +
            EVENTS_ID + " INTEGER," +
            GIFTS_ID + " INTEGER," +
            KEY_CREATED_AT + " DATETIME" + ")";


    public static synchronized DatabaseHelper getInstance(Context context){
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_AGENDA_ITEMS);
        db.execSQL(CREATE_TABLE_CONTACTS);
        db.execSQL(CREATE_TABLE_GIFTS);
        db.execSQL(CREATE_TABLE_CONTACTS_EVENTS);
        db.execSQL(CREATE_TABLE_CONTACTS_GIFTS);
        db.execSQL(CREATE_TABLE_EVENTS_GIFTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AGENDA_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIFTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS_GIFTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS_GIFTS);
        // create new tables
        onCreate(db);
    }

    /*
     * Creating a contact
     */
    public long insertContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        byte[] photoByte;
        if(contact.getProfilePhoto()==null) {
            photoByte=null;
        }
        else{photoByte = DbBitmapUtility.getBytes(contact.getProfilePhoto());}

        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, contact.getName());
        values.put(CONTACT_RELATIONSHIP, contact.getRelationship());
        values.put(CONTACT_IMAGE, photoByte);
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long contactId = db.insert(TABLE_CONTACTS, null, values);
        return contactId;
    }
    public long insertAgendaItem(AgendaItem agendaItem){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        ContentValues values = new ContentValues();
        if(agendaItem.getDate()!=null){
            values.put(EVENT_DATE, dateFormat.format(agendaItem.getDate()));
        }
        else{values.put(EVENT_DATE, getDateTime());}
        values.put(EVENT_TITLE, agendaItem.getTitle());
        values.put(EVENT_RECURRING, agendaItem.getRecurring());
        values.put(EVENT_RECURRATE, agendaItem.getRecurRate());
        values.put(KEY_CREATED_AT, getDateTime());

        long agendaItemId = db.insert(TABLE_AGENDA_ITEMS, null, values);
        return agendaItemId;
    }

    public long insertGift(Gift gift) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(GIFT_NAME, gift.getName());
        values.put(GIFT_URL, gift.getUrl());
        values.put(GIFT_PHOTO_LOCATION, gift.getPhotoLocation());
        values.put(GIFT_NOTES, gift.getNotes());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long contactId = db.insert(TABLE_CONTACTS, null, values);

        return contactId;
    }

    public long tagContactGift(long contactId, long giftId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACTS_ID, contactId);
        values.put(GIFTS_ID, giftId);
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long tagId = db.insert(TABLE_CONTACTS_GIFTS, null, values);
        return tagId;
    }
    public long tagContactEvent(long contactId, long eventId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACTS_ID, contactId);
        values.put(EVENTS_ID, eventId);
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long tagId = db.insert(TABLE_CONTACTS_EVENTS, null, values);
        return tagId;
    }
    public long tagEventGift(long eventId, long giftId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EVENTS_ID, eventId);
        values.put(GIFTS_ID, giftId);
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long tagId = db.insert(TABLE_EVENTS_GIFTS, null, values);
        return tagId;
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        /* Specified record
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE "
                + KEY_ID + " = " + todo_id;
        */
        Log.e(TAG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Contact dbContact = new Contact();
                dbContact.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dbContact.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
                dbContact.setRelationship(c.getString(c.getColumnIndex(CONTACT_RELATIONSHIP)));
                if(c.getBlob(c.getColumnIndex(CONTACT_IMAGE))==null) {}
                else{
                    dbContact.setProfilePhoto(DbBitmapUtility.getImage(c.getBlob(c.getColumnIndex(CONTACT_IMAGE))));
                }
                contacts.add(dbContact);
            } while(c.moveToNext());
            c.moveToFirst();
        }
        return contacts;
    }

    public ArrayList<Gift> getAllGifts() {
        ArrayList<Gift> gifts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_GIFTS;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Gift dbGift = new Gift();
                dbGift.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dbGift.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
                dbGift.setNotes(c.getString(c.getColumnIndex(GIFT_NOTES)));
                dbGift.setPhotoLocation(c.getString(c.getColumnIndex(GIFT_PHOTO_LOCATION)));
                dbGift.setUrl(c.getString(c.getColumnIndex(GIFT_URL)));
                gifts.add(dbGift);
            } while(c.moveToNext());
            c.moveToFirst();
        }
        return gifts;
    }

    public ArrayList<AgendaItem> getAllAgendaItems() {
        ArrayList<AgendaItem> agendaItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_AGENDA_ITEMS;
        /* Specified record
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE "
                + KEY_ID + " = " + todo_id;
        */
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                AgendaItem dbAgendaItem = new AgendaItem();
                dbAgendaItem.setTitle(c.getString(c.getColumnIndex(EVENT_TITLE)));
                try{
                    Date dbDate = new Date(c.getLong(c.getColumnIndex(EVENT_DATE))*1000);
                    dbAgendaItem.setDate(dbDate);
                } catch(ParseException e) {
                    e.printStackTrace();
                }
                int isRecurring = c.getInt(c.getColumnIndex(EVENT_RECURRING));
                dbAgendaItem.setRecurRate(c.getString(c.getColumnIndex(EVENT_RECURRATE)));
                //Handle date type incompatibilities
                if(isRecurring==1){dbAgendaItem.setRecurring(true);}
                else{dbAgendaItem.setRecurring(false);}

                if(c.getBlob(c.getColumnIndex(EVENT_IMAGE))==null) {}
                else{
                    dbAgendaItem.setEventImage(DbBitmapUtility.getImage(c.getBlob(c.getColumnIndex(EVENT_IMAGE))));
                }
                agendaItems.add(dbAgendaItem);
            } while(c.moveToNext());
            c.moveToFirst();
        }
        return agendaItems;
    }

    public AgendaItem getEventById(long recordId) {
        AgendaItem dbAgendaItem = new AgendaItem();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_AGENDA_ITEMS +" WHERE "+ KEY_ID +" = "+ recordId;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            dbAgendaItem.setTitle(c.getString(c.getColumnIndex(EVENT_TITLE)));
            try{
                Date dbDate = new Date(c.getLong(c.getColumnIndex(EVENT_DATE))*1000);
                dbAgendaItem.setDate(dbDate);
            } catch(ParseException e) {
                e.printStackTrace();
            }
            int isRecurring = c.getInt(c.getColumnIndex(EVENT_RECURRING));
            dbAgendaItem.setRecurRate(c.getString(c.getColumnIndex(EVENT_RECURRATE)));
            //Handle date type incompatibilities
            if(isRecurring==1){dbAgendaItem.setRecurring(true);}
            else{dbAgendaItem.setRecurring(false);}

            if(c.getBlob(c.getColumnIndex(EVENT_IMAGE))==null) {}
            else{
                dbAgendaItem.setEventImage(DbBitmapUtility.getImage(c.getBlob(c.getColumnIndex(EVENT_IMAGE))));
            }
        }
        return dbAgendaItem;
    }

    public ArrayList<Contact> getContactTagsForEvent(long eventId){
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  contacts.* FROM " + TABLE_CONTACTS_EVENTS +
                " JOIN "+ TABLE_CONTACTS+" on contacts.id = "+TABLE_CONTACTS_EVENTS+".contacts_id "+
                " WHERE "
                + EVENTS_ID + " = " + eventId;

        Log.e(TAG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Contact dbContact = new Contact();
                dbContact.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dbContact.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
                dbContact.setRelationship(c.getString(c.getColumnIndex(CONTACT_RELATIONSHIP)));
                if(c.getBlob(c.getColumnIndex(CONTACT_IMAGE))==null) {}
                else{
                    dbContact.setProfilePhoto(DbBitmapUtility.getImage(c.getBlob(c.getColumnIndex(CONTACT_IMAGE))));
                }
                contacts.add(dbContact);
            } while(c.moveToNext());
            c.moveToFirst();
        }
        return contacts;
    }
    public ArrayList<Contact> getContactTagsForGift(long giftId){
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  contacts.* FROM " + TABLE_CONTACTS_GIFTS +
                " JOIN "+ TABLE_CONTACTS+" on contacts.id = "+TABLE_CONTACTS_GIFTS+".contacts_id "+
                " WHERE "
                + GIFTS_ID + " = " + giftId;

        Log.e(TAG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Contact dbContact = new Contact();
                dbContact.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dbContact.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
                dbContact.setRelationship(c.getString(c.getColumnIndex(CONTACT_RELATIONSHIP)));
                if(c.getBlob(c.getColumnIndex(CONTACT_IMAGE))==null) {}
                else{
                    dbContact.setProfilePhoto(DbBitmapUtility.getImage(c.getBlob(c.getColumnIndex(CONTACT_IMAGE))));
                }
                contacts.add(dbContact);
            } while(c.moveToNext());
            c.moveToFirst();
        }
        return contacts;
    }
    public ArrayList<Gift> getGiftTagsForContact (long contactId){
        ArrayList<Gift> gifts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  "+TABLE_GIFTS+".* FROM " + TABLE_CONTACTS_GIFTS +
                " JOIN "+ TABLE_GIFTS+" on gifts.id = "+TABLE_CONTACTS_GIFTS+".gifts_id "+
                " WHERE "
                + CONTACTS_ID + " = " + contactId;

        Log.e(TAG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Gift dbGift = new Gift();
                dbGift.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dbGift.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
                dbGift.setNotes(c.getString(c.getColumnIndex(GIFT_NOTES)));
                dbGift.setPhotoLocation(c.getString(c.getColumnIndex(GIFT_PHOTO_LOCATION)));
                dbGift.setUrl(c.getString(c.getColumnIndex(GIFT_URL)));
                gifts.add(dbGift);
            } while(c.moveToNext());
            c.moveToFirst();
        }
        return gifts;
    }
    public ArrayList<Gift> getGiftTagsForEvent (long eventId){
        ArrayList<Gift> gifts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  "+TABLE_GIFTS+".* FROM " + TABLE_EVENTS_GIFTS +
                " JOIN "+ TABLE_GIFTS+" on gifts.id = "+TABLE_EVENTS_GIFTS+".gifts_id "+
                " WHERE "
                + EVENTS_ID + " = " + eventId;

        Log.e(TAG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Gift dbGift = new Gift();
                dbGift.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dbGift.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
                dbGift.setNotes(c.getString(c.getColumnIndex(GIFT_NOTES)));
                dbGift.setPhotoLocation(c.getString(c.getColumnIndex(GIFT_PHOTO_LOCATION)));
                dbGift.setUrl(c.getString(c.getColumnIndex(GIFT_URL)));
                gifts.add(dbGift);
            } while(c.moveToNext());
            c.moveToFirst();
        }
        return gifts;
    }
    public ArrayList<AgendaItem> getEventTagsForContact (long contactId){
        ArrayList<AgendaItem> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  "+TABLE_AGENDA_ITEMS+".* FROM " + TABLE_CONTACTS_EVENTS +
                " JOIN "+ TABLE_AGENDA_ITEMS+" on events_id = "+TABLE_CONTACTS_EVENTS+".events_id "+
                " WHERE "
                + TABLE_CONTACTS_EVENTS + "."+CONTACTS_ID + " = " + contactId;

        Log.e(TAG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                AgendaItem dbEvent = new AgendaItem();
                dbEvent.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dbEvent.setTitle(c.getString(c.getColumnIndex(EVENT_TITLE)));
                dbEvent.setRecurRate(c.getString(c.getColumnIndex(EVENT_RECURRATE)));
                if(c.getInt(c.getColumnIndex(EVENT_RECURRING))>0){dbEvent.setRecurring(true);}
                else{dbEvent.setRecurring(false);}
                if(c.getBlob(c.getColumnIndex(EVENT_IMAGE))==null) {}
                else{
                    dbEvent.setEventImage(DbBitmapUtility.getImage(c.getBlob(c.getColumnIndex(EVENT_IMAGE))));
                }
                events.add(dbEvent);
            } while(c.moveToNext());
            c.moveToFirst();
        }
        return events;
    }
    public ArrayList<AgendaItem> getEventTagsForGift (long giftId){
        ArrayList<AgendaItem> events = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  "+TABLE_AGENDA_ITEMS+".* FROM " + TABLE_EVENTS_GIFTS +
                " JOIN "+ TABLE_AGENDA_ITEMS+" on events_id = "+TABLE_EVENTS_GIFTS+".events_id "+
                " WHERE "
                + TABLE_EVENTS_GIFTS + "."+GIFTS_ID + " = " + giftId;

        Log.e(TAG, selectQuery);
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                AgendaItem dbEvent = new AgendaItem();
                dbEvent.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dbEvent.setTitle(c.getString(c.getColumnIndex(EVENT_TITLE)));
                dbEvent.setRecurRate(c.getString(c.getColumnIndex(EVENT_RECURRATE)));
                if(c.getInt(c.getColumnIndex(EVENT_RECURRING))>0){dbEvent.setRecurring(true);}
                else{dbEvent.setRecurring(false);}
                if(c.getBlob(c.getColumnIndex(EVENT_IMAGE))==null) {}
                else{
                    dbEvent.setEventImage(DbBitmapUtility.getImage(c.getBlob(c.getColumnIndex(EVENT_IMAGE))));
                }
                events.add(dbEvent);
            } while(c.moveToNext());
            c.moveToFirst();
        }
        return events;
    }

    public void insertContactTagsForEvent(long eventId, ArrayList<Integer> selectedTagIds){
        if(selectedTagIds.size()>0){
            SQLiteDatabase db = this.getReadableDatabase();
            for (int i = 0; i < selectedTagIds.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put(CONTACTS_ID,selectedTagIds.get(i));
                cv.put(EVENTS_ID,eventId);
                db.insert(TABLE_CONTACTS_EVENTS,null,cv);
            }
        }

    }
    public void insertContactTagsForGift(long giftId, ArrayList<Integer> selectedTagIds){
        if(selectedTagIds.size()>0){
            SQLiteDatabase db = this.getReadableDatabase();
            for (int i = 0; i < selectedTagIds.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put(CONTACTS_ID,selectedTagIds.get(i));
                cv.put(GIFTS_ID,giftId);
                db.insert(TABLE_CONTACTS_GIFTS,null,cv);
            }
        }
    }
    public void insertEventTagsForContact(long contactId, ArrayList<Integer> selectedTagIds){
        if(selectedTagIds.size()>0){
            SQLiteDatabase db = this.getReadableDatabase();
            for (int i = 0; i < selectedTagIds.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put(EVENTS_ID,selectedTagIds.get(i));
                cv.put(CONTACTS_ID,contactId);
                db.insert(TABLE_CONTACTS_EVENTS,null,cv);
            }
        }
    }
    public void insertEventTagsForGift(long giftId, ArrayList<Integer> selectedTagIds){
        if(selectedTagIds.size()>0){
            SQLiteDatabase db = this.getReadableDatabase();
            for (int i = 0; i < selectedTagIds.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put(EVENTS_ID,selectedTagIds.get(i));
                cv.put(GIFTS_ID,giftId);
                db.insert(TABLE_EVENTS_GIFTS,null,cv);
            }
        }
    }
    public void insertGiftTagsForEvent(long eventId, ArrayList<Integer> selectedTagIds){
        if(selectedTagIds.size()>0){
            SQLiteDatabase db = this.getReadableDatabase();
            for (int i = 0; i < selectedTagIds.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put(GIFTS_ID,selectedTagIds.get(i));
                cv.put(EVENTS_ID,eventId);
                db.insert(TABLE_EVENTS_GIFTS,null,cv);
            }
        }
    }
    public void insertGiftTagsForContact(long contactId, ArrayList<Integer> selectedTagIds){
        if(selectedTagIds.size()>0){
            SQLiteDatabase db = this.getReadableDatabase();
            for (int i = 0; i < selectedTagIds.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put(GIFTS_ID,selectedTagIds.get(i));
                cv.put(CONTACTS_ID,contactId);
                db.insert(TABLE_CONTACTS_GIFTS,null,cv);
            }
        }
    }

    public void deleteContactTagsForEvent(long eventId){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_CONTACTS_EVENTS,EVENTS_ID+"="+eventId,null);
    }
    public void deleteContactTagsForGift(long giftId){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_CONTACTS_GIFTS,GIFTS_ID+"="+giftId,null);
    }
    public void deleteGiftTagsForEvent(long eventId){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_EVENTS_GIFTS,EVENTS_ID+"="+eventId,null);
    }
    public void deleteGiftTagsForContact(long contactId){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_CONTACTS_GIFTS,CONTACTS_ID+"="+contactId,null);
    }
    public void deleteEventTagsForContact(long contactId){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_CONTACTS_EVENTS,EVENTS_ID+"="+contactId,null);
    }
    public void deleteEventTagsForGift(long giftId){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_EVENTS_GIFTS,GIFTS_ID+"="+giftId,null);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
