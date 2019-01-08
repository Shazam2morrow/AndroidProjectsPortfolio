package com.hfad.portfolioprojectkz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class PortfolioProjectDatabaseHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "portfolioProjectDB";
    private static final int DB_VERSION = 1;

    //Initialize db
    PortfolioProjectDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (oldVersion < 1)
        {
            db.execSQL("CREATE TABLE ACCOUNT (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NAME TEXT, SURNAME TEXT, TEL TEXT, PASS TEXT, EMAIL TEXT, BIRTH TEXT, " +
                    "GENDER NUMERIC, CITY TEXT, CARD_NUMBER TEXT, PHOTO_ID INTEGER, LOGGED_IN NUMERIC);");
            db.execSQL("CREATE TABLE PIZZA (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NAME TEXT, DESCRIPTION TEXT, PHOTO_ID INTEGER)");
            db.execSQL("CREATE TABLE WOKE (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NAME TEXT, DESCRIPTION TEXT, PHOTO_ID INTEGER)");
            db.execSQL("CREATE TABLE SUSHI (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NAME TEXT, DESCRIPTION TEXT, PHOTO_ID INTEGER)");
            db.execSQL("CREATE TABLE DRINKS (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "NAME TEXT, DESCRIPTION TEXT, PHOTO_ID INTEGER)");

            insertAccountInfo(db, "John", "Weak", "+77043332211", "admin",
                    "john.weak@gmail.com", "12-09-89", 0, "New-York",
                    "88005553555", 5, 0);
        }
    }

    private static void insertAccountInfo(SQLiteDatabase db, String name, String surname,
                String tel, String pass, String email, String birth, int gender, String city,
                                          String card_number, int photoId, int loggedIn)
    {
        ContentValues accountInfo = new ContentValues();
        accountInfo.put("NAME", name);
        accountInfo.put("SURNAME", surname);
        accountInfo.put("TEL", tel);
        accountInfo.put("PASS", pass);
        accountInfo.put("EMAIL", email);
        accountInfo.put("BIRTH", birth);
        accountInfo.put("GENDER", gender);
        accountInfo.put("CITY", city);
        accountInfo.put("CARD_NUMBER", card_number);
        accountInfo.put("PHOTO_ID", photoId);
        accountInfo.put("LOGGED_IN", loggedIn);

        db.insert("ACCOUNT", null, accountInfo);
    }

    private static void insertPizzaInfo()
    {
    }

    private static void insertWokeInfo()
    {
    }

    private static void insertSushiInfo()
    {
    }

    private static void insertDrinksInfo()
    {
    }
}
