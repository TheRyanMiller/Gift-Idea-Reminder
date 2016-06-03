package com.rtmillerprojects.giftideareminder.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rtmillerprojects.giftideareminder.model.AgendaItem;
import com.rtmillerprojects.giftideareminder.model.Contact;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Ryan on 5/31/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "giftymcgiftface.db";
    public static final String TABLE_AGENDA_ITEMS = "agenda_items";
    public static final String TABLE_CONTACTS = "contacts";
    public static final String TABLE_GIFTS = "gifts";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "MESSAGE";
    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
    // Agenda items columns
    private static final String EVENT_DATE = "date";
    // Contacts columns
    private static final String CONTACT_NAME = "name";
    private static final String CONTACT_RELATIONSHIP = "relationship";
    // Gifts columns
    private static final String GIFT_NAME = "name";
    private static final String URL = "url";
    private static final String PHOTO_LOCATION = "photo_location";


    private static final String CREATE_TABLE_AGENDA_ITEMS = "CREATE TABLE "
            + TABLE_AGENDA_ITEMS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + EVENT_DATE
            + " DATE," + KEY_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
            + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + CONTACT_NAME
            + " TEXT," + CONTACT_RELATIONSHIP + " TEXT," + KEY_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_GIFTS = "CREATE TABLE "
            + TABLE_GIFTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + GIFT_NAME
            + " TEXT," + URL + " TEXT," + PHOTO_LOCATION + " TEXT," + KEY_CREATED_AT
            + " DATETIME" + ")";


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
     * Creating a todo
     */
    public long insertContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACT_NAME, contact.getName());
        values.put(CONTACT_RELATIONSHIP, contact.getRelationship());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long contactId = db.insert(TABLE_CONTACTS, null, values);

        return contactId;
    }
    public long insertAgendaItem(AgendaItem agendaItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long agendaItemId = db.insert(TABLE_AGENDA_ITEMS, null, values);
        return agendaItemId;
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
