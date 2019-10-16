package com.example.myproject.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Context context;
    ArrayList<Data> arrayList;
    //sessioninitialize

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //db
     String username;
    MyDatabase myDatabase;

    public RecyclerAdapter(Context context, ArrayList<Data> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        //session and db
        sharedPreferences = context.getSharedPreferences("myUserData", MODE_PRIVATE);
        editor = sharedPreferences.edit();
       username = sharedPreferences.getString("username", "");
        myDatabase = new MyDatabase(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row, viewGroup, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
//username->session get

        final Data data = arrayList.get(i);
        myViewHolder.textView.setText(data.getNm1());
        myViewHolder.imageView.setImageResource(data.getImg());
        myViewHolder.textView.setTextColor(context.getResources().getColor(data.getColor()));


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Toast.makeText(context, ""+data.getNm1(), Toast.LENGTH_SHORT).show();*/

                Log.d("ddddd",""+data.getNm1());
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final EditText editText = new EditText(context);
                builder.setView(editText);
                if (data.getNm1().equalsIgnoreCase( "Mute Mode")) {

                    String value = "";
                    value = myDatabase.getSilent(username);
                    Log.d("ddddd", "" + value);
                    if (value != null && !(value.isEmpty())) {
                        editText.setText("" + value);
                    }
                }

                if (data.getNm1() == "Phonebook Mode") {

                    editText.setText("Test");
                    editText.setFocusable(false);
                }
                if (data.getNm1().equalsIgnoreCase("Ringer Mode")) {

                    String value = "";
                    value = myDatabase.getNormal(username);
                    Log.d("dddddring", "" + value);
                    if (value != null && !(value.isEmpty())) {
                        editText.setText("" + value);
                    }



                }

                if (data.getNm1().equalsIgnoreCase("Divert Mode On")) {

                    String value = "";
                    value = myDatabase.getDivertOn(username);
                    Log.d("dddddring", "" + value);
                    if (value != null && !(value.isEmpty())) {
                        editText.setText("" + value);
                    }



                } if (data.getNm1().equalsIgnoreCase("Divert Mode Off")) {

                    String value = "";
                    value = myDatabase.getDivertOff(username);
                    Log.d("dddddring", "" + value);
                    if (value != null && !(value.isEmpty())) {
                        editText.setText("" + value);
                    }



                }if (data.getNm1().equalsIgnoreCase("Location Mode")) {

                    String value = "";
                    value = myDatabase.getLocation(username);
                    Log.d("dddddring", "" + value);
                    if (value != null && !(value.isEmpty())) {
                        editText.setText("" + value);
                    }



                }
                builder.setTitle("Enter Passcode");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //if mute mode
//db.insertsilent(edittext.getText.tostring(),username)

                        if (data.getNm1() == "Mute Mode") {
                            if (myDatabase.getPasscodeData(username)) {

                                myDatabase.updateSilent(username, editText.getText().toString());
                            }
                            else
                            {
                                myDatabase.insertSilent(username,  editText.getText().toString());

                            }
                        }

                        if (data.getNm1() =="Ringer Mode")
                        {
                            if (myDatabase.getPasscodeData(username)) {

                                myDatabase.updateNormal(username, editText.getText().toString());
                            }
                            else
                            {
                                myDatabase.insertNormal(username,  editText.getText().toString());

                            }
                        }




                        if (data.getNm1() == "Location Mode") {
                            if (myDatabase.getPasscodeData(username)) {
                                myDatabase.updateLocation(username,editText.getText().toString());
                            }
                            else
                            {
                                myDatabase.insertAddress(username, editText.getText().toString());

                            }
                        }
                        if (data.getNm1() == "Divert Mode On") {

                            if (myDatabase.getPasscodeData(username)) {
                                myDatabase.updateDiverOn(username,editText.getText().toString());
                            }
                            else {
                                myDatabase.insertDivertOn(username, editText.getText().toString());

                            }
                        }
                        if (data.getNm1() == "Divert Mode Off") {

                            if (myDatabase.getPasscodeData(username)) {
                                myDatabase.updateDivertOff(username,editText.getText().toString());
                            }
                            else {
                                myDatabase.insertDivertOff(username, editText.getText().toString());

                            }

                        }
                    }
                }).setCancelable(false);
                builder.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.tv);
        }
    }
}

