package com.rtmillerprojects.giftideareminder.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rtmillerprojects.giftideareminder.model.AgendaItem;
import com.rtmillerprojects.giftideareminder.model.Contact;
import com.rtmillerprojects.giftideareminder.model.Gift;

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
    private static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "giftymcgiftface.db";
    public static final String TABLE_AGENDA_ITEMS = "agenda_items";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_GIFTS = "gifts";
    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    // Agenda items columns
    private static final String EVENT_DATE = "date";
    private static final String EVENT_TITLE = "title";
    private static final String EVENT_RECURRING = "recurring";
    private static final String EVENT_RECURRATE = "recurrate";
    // Contacts columns
    private static final String CONTACT_NAME = "name";
    private static final String CONTACT_IMAGE = "image";
    private static final String CONTACT_RELATIONSHIP = "relationship";
    // Gifts columns
    private static final String GIFT_NAME = "name";
    private static final String URL = "url";
    private static final String PHOTO_LOCATION = "photo_location";
    private static final String GIFT_NOTES = "notes";


    private static final String CREATE_TABLE_AGENDA_ITEMS = "CREATE TABLE " +
            TABLE_AGENDA_ITEMS + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
            EVENT_DATE + " DATE," +
            EVENT_RECURRING + " BOOLEAN," +
            EVENT_RECURRATE + " TEXT," +
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
            URL + " TEXT," +
            PHOTO_LOCATION + " TEXT," +
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AGENDA_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GIFTS);
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
        values.put(EVENT_DATE, dateFormat.format(agendaItem.getDate()));
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
        values.put(URL, gift.getUrl());
        values.put(PHOTO_LOCATION, gift.getPhotoLocation());
        values.put(GIFT_NOTES, gift.getNotes());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long contactId = db.insert(TABLE_CONTACTS, null, values);

        return contactId;
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


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
