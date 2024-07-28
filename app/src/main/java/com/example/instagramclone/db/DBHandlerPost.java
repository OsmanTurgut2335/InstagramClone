package com.example.instagramclone.db;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.instagramclone.Art.PostDetails;
import com.example.instagramclone.user.Username;

import java.util.ArrayList;


public class DBHandlerPost extends SQLiteOpenHelper {
    private static final String DB_NAME = "Posts";

    private static final String Username = "Username";

    private static final String ArtName = "ArtName";

    private static final String ArtYear = "ArtYear";

    private static final String ArtistName = "ArtistName";

     private static final String ImageBlob = "ImageBlob";

    private static final String ArtUrlx = "ArtUrlx";

    private static final String ArtUrly = "ArtUrly";

    private static final String Password = "Password";


    // below int is our database version
    private static final int DB_VERSION = 5;


    private static final String TABLE_NAME_FOR_USERS = "users";
    private static final String TABLE_NAME = "postss";
    private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FOR_USERS + "("
            + Username + " TEXT,"
            + Password + " TEXT)";


PostDetails emptyPostDetails = new PostDetails("empty","empty",null,null,0,0,null);  //this is for checking if the user post table is empty. the function will return this if the table is empty.

    public DBHandlerPost(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {




        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + Username + " TEXT,"
                + ArtName + " TEXT,"
                + ArtistName + " TEXT,"
                + ArtYear + " TEXT,"
                + ArtUrlx + " INTEGER,"
                + ArtUrly + " INTEGER,"
                + ImageBlob + " BLOB)";

        db.execSQL(query);




        db.execSQL(CREATE_USER_TABLE);


    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



public void addNewUser(String username,String password){
        // add a new user in login screen

    SQLiteDatabase db = this.getWritableDatabase();



    ContentValues values = new ContentValues();

    // on below line we are passing all values
    // along with its key and value pair.
    values.put(Username, username);
    values.put(Password, password);


    // after adding all values we are passing
    // content values to our table.
    db.insert(TABLE_NAME_FOR_USERS, null, values);


    // at last we are closing our
    // database after adding database.
    db.close();


}


    public void addNewPost(String username, String artName, String artistName, String artYear, Integer artUrlx, Integer artUrly, byte[] imageByteArray){
        // add a new user in login screen

        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues values = new ContentValues();

        values.put(Username, username);
        values.put(ArtName, artName);
        values.put(ArtistName, artistName);
        values.put(ImageBlob, imageByteArray);
        values.put(ArtYear, artYear);
        values.put(ArtUrlx, artUrlx);
        values.put(ArtUrly, artUrly);


        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);


        // at last we are closing our
        // database after adding database.
        db.close();



    }
    public ArrayList<com.example.instagramclone.user.Username> readUsers() {

        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();



        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME_FOR_USERS, null);

        // on below line we are creating a new array list.
        ArrayList<Username> courseModalArrayList = new ArrayList<>();



        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                System.out.println("SA BEYKER  BAK BURAYAA "  + cursorCourses.getString(1));

                courseModalArrayList.add(new Username(cursorCourses.getString(0),
                        cursorCourses.getString(1) ));
                // System.out.println("AMCIK "+ cursorCourses.getString(3) );

            } while (cursorCourses.moveToNext());
            // moving our cursor to next.


        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }

    public ArrayList<PostDetails> readUserPosts(String username ) {

        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME + "  WHERE " + Username +"='"+username+"'", null);

        // on below line we are creating a new array list.
        ArrayList<PostDetails> courseModalArrayList = new ArrayList<>();
        ArrayList<PostDetails> emptyArrayList = new ArrayList<>();
        emptyArrayList.add(emptyPostDetails);



        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                //  System.out.println("SA BEYKER  BAK BURAYAA "  + cursorCourses.getString(1));
                System.out.println("SİK YARAK AM"+cursorCourses.getCount());

                courseModalArrayList.add(new PostDetails(cursorCourses.getString(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getInt(4),
                        cursorCourses.getInt(5),
                        cursorCourses.getBlob(6) ));
                // System.out.println("AMCIK "+ cursorCourses.getString(3) );

            } while (cursorCourses.moveToNext());
            // moving our cursor to next.



        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();

            return courseModalArrayList;
    }

       return emptyArrayList;
    }

    public void deleteDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME_FOR_USERS);
        db.execSQL("delete from " + TABLE_NAME);
        db.close();

    }
    public ArrayList<PostDetails> readSpecificPost(String artName ) {
        //adaptördeki posta tıklanınca açılması için bunu kullancaz

        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + ArtName +"='"+artName+"'", null);

        // on below line we are creating a new array list.
        ArrayList<PostDetails> courseModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                //  System.out.println("SA BEYKER  BAK BURAYAA "  + cursorCourses.getString(1));

                courseModalArrayList.add(new PostDetails(cursorCourses.getString(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getInt(4),
                        cursorCourses.getInt(5),
                        cursorCourses.getBlob(6) ));
                // System.out.println("AMCIK "+ cursorCourses.getString(3) );

            } while (cursorCourses.moveToNext());
            // moving our cursor to next.


        }
        // at last closing our cursor
        // and returning our array list.
        cursorCourses.close();
        return courseModalArrayList;
    }

    public boolean doesTableExist(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_NAME + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    public boolean doesPostTableExist() {
        SQLiteDatabase db = this.getReadableDatabase();

            if(db == null || !db.isOpen()) {
                db = getReadableDatabase();
            }

            if(!db.isReadOnly()) {
                db.close();
                db = getReadableDatabase();
            }

try {
    String query = "select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TABLE_NAME_FOR_USERS+"'";
    try (Cursor cursor = db.rawQuery(query, null)) {
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
        }
        return false;
    }
} catch (Exception e) {
    e.printStackTrace();
}
return  false;
    }
        /*
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_NAME_FOR_USERS + "'", null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {

                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;

        */


    }



