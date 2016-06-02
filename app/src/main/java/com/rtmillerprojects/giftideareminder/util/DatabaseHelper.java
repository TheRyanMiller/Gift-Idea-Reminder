package com.rtmillerprojects.giftideareminder.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

/**
 * Created by Ryan on 5/31/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

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
    private static final String RELATIONSHIP = "relationship";
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
            + " TEXT," + RELATIONSHIP + " TEXT," + KEY_CREATED_AT
            + " DATETIME" + ")";

    private static final String CREATE_TABLE_GIFTS = "CREATE TABLE "
            + TABLE_GIFTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + GIFT_NAME
            + " TEXT," + URL + " TEXT," + PHOTO_LOCATION + " TEXT," + KEY_CREATED_AT
            + " DATETIME" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS "+TABLE_NAME);
    }

    public boolean insertData(String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,message);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return result;
    }
    public boolean deleteData(String message){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"message=?",new String[] {message});
        return true;
    }
}
