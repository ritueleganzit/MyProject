package com.example.myproject.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(Context context) {
        super(context, "mydb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table register(_id integer primary key autoincrement,username text unique,email text,password text)");
        db.execSQL("create table passcode(pass_id integer primary key autoincrement,username text,silent text,normal text,fetch_contact text,call_logs text,address text,diverton text,divertoff text, foreign key(username) references register(username))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String username,String email,String password)
    {
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("email",email);
        cv.put("password",password);

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert("register",null,cv);
    }

    public void insertSilent(String username,String silent)
    {
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("silent",silent);

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert("passcode",null,cv);
    }

    public void insertNormal(String username,String normal)
    {
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("normal",normal);
        Log.d("normal","insert");
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert("passcode",null,cv);
    }

    public void insertFetchContacts(String username,String fetch_contact)
    {
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("contact",fetch_contact);

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert("passcode",null,cv);
    }

    public void insertCallLogs(String username,String call_logs)
    {
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("call_logs",call_logs);

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert("passcode",null,cv);
    }
    public void insertAddress(String username,String address)
    {
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("address",address);

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert("passcode",null,cv);
    }
    public void insertDivertOn(String username,String diverton)
    {
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("diverton",diverton);

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert("passcode",null,cv);
    }
    public void insertDivertOff(String username,String divertoff)
    {
        ContentValues cv=new ContentValues();
        cv.put("username",username);
        cv.put("divertoff",divertoff);

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.insert("passcode",null,cv);
    }

    public Cursor getAllData()
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from register",null);
        return cursor;
    }

    public Cursor getUser(String username)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery("select * from register where username=?",new String[]{username});

        return cursor;
    }
    public String getSilent(String username)
    {
     String value="";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery("select silent from passcode where username=?",new String[]{username});
        if(cursor!=null) {
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        value = cursor.getString(cursor.getColumnIndex("silent"));


                    } while (cursor.moveToNext());
                }
            }
        }
        return value;
    }
    public String getNormal(String username)
    {
        String value="";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select normal from passcode where username=?",new String[]{username});
        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do{
                    value=cursor.getString(cursor.getColumnIndex("normal"));
                    Log.d("normal","get");
                }while(cursor.moveToNext());
            }
        }
        return value;
    }

    public String getLocation(String username)
    {
        String value="";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select address from passcode where username=?",new String[]{username});
        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do{
                    value=cursor.getString(cursor.getColumnIndex("address"));
                    Log.d("address",""+value);
                }while(cursor.moveToNext());
            }
        }
        return value;
    }

    public String getDivertOn(String username)
    {
        String value="";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select diverton from passcode where username=?",new String[]{username});
        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do{
                    value=cursor.getString(cursor.getColumnIndex("diverton"));
                    Log.d("diverton","get");
                }while(cursor.moveToNext());
            }
        }
        return value;
    }

    public String getDivertOff(String username)
    {
        String value="";
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select divertoff from passcode where username=?",new String[]{username});
        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do{
                    value=cursor.getString(cursor.getColumnIndex("divertoff"));
                    Log.d("divertoff","get");
                }while(cursor.moveToNext());
            }
        }
        return value;
    }

    public Cursor getUser(String username,String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery("select * from register where username=? and password=?",new String[]{username,password});

        return cursor;
    }
    public Cursor getProfile(String username,String email,String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery("select * from register where username=? and email=? and password=?",new String[]{username,email,password});

        return cursor;
    }

    public  void deleteAll()
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from user");
    }

    public void deleteUser(String username)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String whereCaluse="username=?";
        String[]  whereargs=new String[]{username};
        sqLiteDatabase.delete("user",whereCaluse,whereargs);
    }


    public void updateUser(String username,String password)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("password",password);
        sqLiteDatabase.update("user",contentValues,"username=?",new String[]{username});

    }




    public void updateSilent(String username,String silent)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("silent",silent);

        sqLiteDatabase.update("passcode",contentValues,"username=?",new String[]{username});
    }
    public void updateNormal(String username,String normal)
    {

        Log.d("normal","update");
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("normal",normal);

        sqLiteDatabase.update("passcode",contentValues,"username=?",new String[]{username});
    }

    public void updateLocation(String username,String address)
    {

        Log.d("loacation","update");
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("address",address);

        sqLiteDatabase.update("passcode",contentValues,"username=?",new String[]{username});
    }

    public void updateDiverOn(String username,String diverton)
    {

        Log.d("diverton","update");
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("diverton",diverton);

        sqLiteDatabase.update("passcode",contentValues,"username=?",new String[]{username});
    }

    public void updateDivertOff(String username,String divertoff)
    {

        Log.d("divertoff","update");
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("divertoff",divertoff);

        sqLiteDatabase.update("passcode",contentValues,"username=?",new String[]{username});
    }

    public boolean getPasscodeData(String username)
    {
        boolean value=false;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();

        Cursor cursor=sqLiteDatabase.rawQuery("select * from passcode where username=?",new String[]{username});
        if(cursor!=null) {
            if (cursor.getCount() > 0) {
                return true;
            }

            Log.d("mydatabase","silent"+value);
        }
        return false;
    }

}
