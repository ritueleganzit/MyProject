package com.example.myproject.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
TextView textView1,textView2,textView3;
EditText editText,ed2;
Button button;
    MyDatabase myDatabase;
    Cursor cursor;

SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
String data;

MySMSReceiver  mySMSReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withActivity(MainActivity.this)
                .withPermissions(Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NOTIFICATION_POLICY,
                        Manifest.permission.SEND_SMS).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Intent in = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                }
            }


            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).onSameThread()
        .check();

        editText=(EditText)findViewById(R.id.ed1_uname);
        ed2=(EditText)findViewById(R.id.ed2_pass);
        button=(Button)findViewById(R.id.btn_login);
        myDatabase= new MyDatabase(MainActivity.this);
        sharedPreferences=getSharedPreferences("myUserData",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        mySMSReceiver=new MySMSReceiver();
        registerReceiver(mySMSReceiver,new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));


        data=sharedPreferences.getString("username","");
        if (!(data.equalsIgnoreCase("")))

        {
            Intent intent=new Intent(MainActivity.this,MenuPage.class);
            startActivity(intent);
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.getText().toString().equalsIgnoreCase(""))

                {
                    editText.setError("Please Enter Username");
                }
                else if(ed2.getText().toString().equalsIgnoreCase(""))
                {
                    ed2.setError("Please enter valid password");
                }
                else {
                        cursor=myDatabase.getUser(editText.getText().toString(),ed2
                    .getText().toString());

                    if (cursor.getCount()>0) {
                        if (cursor.moveToFirst()) {
                            do {
                                    editor.putString("username", editText.getText().toString());
                                    editor.commit();
                                    Intent intent = new Intent(MainActivity.this, MenuPage.class);
                                    startActivity(intent);
                                    finish();
                            } while (cursor.moveToNext());
                        }
                    }

                    else{
                        Toast.makeText(MainActivity.this, "Username or password does not match", Toast.LENGTH_SHORT).show();
                    }

                    }

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mySMSReceiver!=null) {
            unregisterReceiver(mySMSReceiver);
        }
    }

    public void Click(View view) {
        Intent intent=new Intent(MainActivity.this,Registeration.class);
        startActivity(intent);
    }

}
