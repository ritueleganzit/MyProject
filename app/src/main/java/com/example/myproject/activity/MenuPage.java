package com.example.myproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.myproject.R;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;

public class MenuPage extends AppCompatActivity {
RecyclerView recyclerView;
ArrayList<Data> arrayList=new ArrayList<>();
TextView textView;
Button button;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_menu_page);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.rc);

        textView=(TextView)findViewById(R.id.tv);



       /* button=(Button)findViewById(R.id.btn_logout);*/

        SharedPreferences sharedPreferences=getSharedPreferences("myUserData",MODE_PRIVATE);
        editor=sharedPreferences.edit();

        String username=sharedPreferences.getString("username","");
        if(username.equalsIgnoreCase("")){
            Intent intent=new Intent(MenuPage.this, MainActivity.class);
            startActivity(intent);
        }

        textView.setText("Welcome "+sharedPreferences.getString("username",""));

        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();
                startActivity(new Intent(MenuPage.this,MainActivity.class));
                finish();
            }
        });
*/
        Data data1=new Data("Ringer Mode",(R.mipmap.ringer),(R.color.menu_blue));
        Data data2=new Data("Mute Mode",(R.mipmap.mute),(R.color.menu_purple));
        Data data3=new Data("Phonebook Mode",(R.mipmap.phonebook),(R.color.menu_orange));
        Data data4=new Data("Location Mode",(R.mipmap.location),(R.color.menu_pink));
        Data data5=new Data("Divert Mode On",(R.mipmap.divert),(R.color.menu_yellow));
        Data data6=new Data("Divert Mode Off",(R.mipmap.calllog),(R.color.menu_green));

        arrayList.add(data1);
        arrayList.add(data2);
        arrayList.add(data3);
        arrayList.add(data4);
        arrayList.add(data5);
        arrayList.add(data6);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(MenuPage.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new RecyclerAdapter(this,arrayList));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

getMenuInflater().inflate(R.menu.myoptionsmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int value=item.getItemId();
        switch (value)
        {
            case R.id.logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                editor.clear();
                editor.commit();
                startActivity(new Intent(MenuPage.this,MainActivity.class));
                finish();
                break;

            case R.id.myprofile:
                Intent intent=new Intent(MenuPage.this,Myprofile.class);
                startActivity(intent);
                break;
        }



        return true;
    }
}
