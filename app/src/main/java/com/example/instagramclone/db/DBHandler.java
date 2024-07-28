package com.example.instagramclone.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.instagramclone.Art.Art;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "Osman";

    // below int is our database version
    private static final int DB_VERSION = 2;

    private static final String TABLE_NAME = "Asmın";

    private static final String Password = "Password";
    private static final String Username = "Username";





    public DBHandler(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {





        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + Username + " TEXT,"
                + Password + " TEXT)";


        db.execSQL(query);


/*
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                +  Username  +"TEXT,"
                +   Password   +"TEXT,"
                + ARTNAME_COL + "TEXT,"
                +ARTISTNAME_COL+ "TEXT,"
                + Artyear + "TEXT,"

                + artUrl + "INTEGER)";*/

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public  void addNewUser(String username, String password){
        deleteDatabase();

        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(Username, username);
        values.put(Password, password);


        // after adding all values we are passing
        // content values to our table.
      db.insert(TABLE_NAME, null, values);


        // at last we are closing our
        // database after adding database.
        db.close();
    }
    public ArrayList<Art> readCourses() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();



        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<Art> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.


                courseModalArrayList.add(new Art(cursorCourses.getString(0),
                        cursorCourses.getString(1) ));


            } while (cursorCourses.moveToNext());
            // moving our cursor to next.


        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }


    public void deleteDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }


    public void addNewPost(String artName,String artistName,String artYear,String artUrl){
        deleteDatabase();

        SQLiteDatabase db = this.getWritableDatabase();
        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
       // values.put(Username, username);
       // values.put(Password, password);


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);


        // at last we are closing our
        // database after adding database.
        db.close();
    }

}
