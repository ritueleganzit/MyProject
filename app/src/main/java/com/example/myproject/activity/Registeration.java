package com.example.myproject.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myproject.R;

public class Registeration extends AppCompatActivity {

    EditText ed1_uname,ed2_email,ed3_pass,ed4_pass;
    Button btn_signup;

    MyDatabase myDatabase;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        ed1_uname=(EditText)findViewById(R.id.ed1_uname);
        myDatabase=new MyDatabase(Registeration.this);
        ed2_email=(EditText)findViewById(R.id.ed_email);
        ed3_pass=(EditText)findViewById(R.id.ed_pass1);
        ed4_pass=(EditText)findViewById(R.id.ed_pass2);
        btn_signup=(Button)findViewById(R.id.signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String name=ed1_uname.getText().toString();
                final String email =ed2_email.getText().toString();
                final String password =ed3_pass.getText().toString();
                final String confpassword =ed4_pass.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(name.length()==0)
                {
                    ed1_uname.requestFocus();
                    ed1_uname.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!(email.matches(emailPattern)))
                {
                    ed2_email.requestFocus();
                    ed2_email.setError("INCORRECT EMAIL");
                }
                else if(!(password.equals(confpassword)))
                {
                    ed4_pass.requestFocus();
                    ed4_pass.setError("INCORRECT PASSWORD");
                }
                else
                {
                    myDatabase.insertData(ed1_uname.getText().toString(),ed2_email.getText().toString(),ed3_pass.getText().toString());
                    Toast.makeText(Registeration.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Registeration.this,MainActivity.class);
                    startActivity(intent);
                }

            }
        });
        cursor=myDatabase.getAllData();
        if(cursor.getCount()>0)
        {
            if(cursor.moveToFirst())
            {
                do{
                    String username=cursor.getString(1);
                    String email=cursor.getString(2);
                    String password=cursor.getString(3);
                    Log.d("username","--->"+username);
                    Log.d("email","--->"+email);
                    Log.d("password","--->"+password);
                }while(cursor.moveToNext());
            }
        }
    }
}
