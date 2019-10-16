package com.example.myproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.myproject.R;

public class Myprofile extends AppCompatActivity {
MyDatabase myDatabase;
SharedPreferences sharedPreferences;
String username,password,email;
SharedPreferences.Editor editor;
EditText ed1,ed2,ed3;
Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        ed1=(EditText)findViewById(R.id.uname);
        ed2=(EditText)findViewById(R.id.password);
        ed3=(EditText)findViewById(R.id.email);
        myDatabase= new MyDatabase(Myprofile.this);
        sharedPreferences=getSharedPreferences("myUserData",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        cursor=myDatabase.getUser(sharedPreferences.getString("username",""));
        if(cursor!=null)
        {
            if (cursor.getCount()>0)
            {
                if (cursor.moveToFirst())
                {
                    do {
                        username=cursor.getString(cursor.getColumnIndex("username"));
                        email=cursor.getString(cursor.getColumnIndex("email"));
                        password=cursor.getString(cursor.getColumnIndex("password"));


                    }while (cursor.moveToNext());
                }
            }
        }

        ed1.setText(""+username);
        ed3.setText(""+email);
        ed2.setText(""+password);

    }

    public void help(View view) {
    }

    /*public void logout(View view) {
        Intent intent=new Intent(Myprofile.this,MainActivity.class);
        startActivity(intent);
    }*/
}
