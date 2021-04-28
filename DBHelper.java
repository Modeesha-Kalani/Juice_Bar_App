package com.example.fruzo_juice_bar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context,"UserData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create TABLE UserDetails (email TEXT primary key, userName TEXT, contactNumber TEXT, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop TABLE if exists UserDetails");
    }

    public boolean insertData(String email, String username, String contactN, String pw){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Email", email);
        contentValues.put("UserName", username);
        contentValues.put("Contact Number", contactN);
        contentValues.put("Password", pw);

        Long result = DB.insert("UserDetails",null,contentValues);

        if (result==-1){
            return false;
        }else{
            return true;
        }
    }

    public String getData(String username){
        SQLiteDatabase DB = this.getWritableDatabase();
        String password = String.valueOf(DB.rawQuery("Select Password From UserDetails Where UserName = ?",new String[]{username}));
        return password;
    }
}
