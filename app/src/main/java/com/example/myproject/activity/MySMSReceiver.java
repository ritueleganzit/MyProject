package com.example.myproject.activity;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class MySMSReceiver extends BroadcastReceiver {
    Bundle bundle;
    private AudioManager audioManager;
    String silent = "", normal = "",location="",divert="",calllog="", num;
    LocationManager locationManager;

    MyDatabase myDatabase;
    SharedPreferences sharedPreferences;
    private String divertoff="";
    Context context;
    LocationManager locationmanager;
    Criteria cri;
    @Override
    public void onReceive(final Context context, Intent intent) {
        myDatabase = new MyDatabase(context);
        this.context=context;
        sharedPreferences = context.getSharedPreferences("myUserData", MODE_PRIVATE);
        locationmanager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        cri = new Criteria();
        silent = myDatabase.getSilent(sharedPreferences.getString("username", ""));
        normal = myDatabase.getNormal(sharedPreferences.getString("username", ""));
        location = myDatabase.getLocation(sharedPreferences.getString("username", ""));
        divert = myDatabase.getDivertOn(sharedPreferences.getString("username", ""));
        divertoff = myDatabase.getDivertOff(sharedPreferences.getString("username", ""));
      //  calllog = myDatabase.getCalllog(sharedPreferences.getString("username", ""));
        bundle = intent.getExtras();
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (bundle != null) {
            Object[] objects = (Object[]) bundle.get("pdus");
            for (int i = 0; i < objects.length; i++) {
                SmsMessage currentmsg = SmsMessage.createFromPdu((byte[]) objects[i]);
                final String phone = currentmsg.getDisplayOriginatingAddress();
                String msg = currentmsg.getDisplayMessageBody();

                if (msg != null && !msg.isEmpty()) {
                    if (msg.trim().equalsIgnoreCase("" + silent)) {
                        Log.d("content", "normal" + silent);
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);


                    }
                    if (msg.trim().equalsIgnoreCase("" + normal)) {
                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);


                    }

                    ContentResolver contentResolver = context.getContentResolver();
                    Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + "=?", new String[]{msg.trim()}, null);
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Log.d("Mycontact", "" + cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                                } while (cursor.moveToNext());
                            }
                        }
                    }





                    if (num!=null && !(num.isEmpty()))
                    {

                        SmsManager smsManager=SmsManager.getDefault();

                        smsManager.sendTextMessage(phone,null,num,null,null);

                    }



                    if (msg.equalsIgnoreCase(location))
                    {




                            if (ContextCompat.checkSelfPermission(context,
                                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {

                              //  Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();


                                String provider = locationmanager.getBestProvider(cri, false);
                                if (locationmanager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


                                    locationmanager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, new LocationListener() {
                                        @Override
                                        public void onLocationChanged(Location location) {



                                            Geocoder geocoder=new Geocoder(context, Locale.getDefault());

                                            try {
                                                List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                                                if (addressList!=null)
                                                {
                                                    StringBuilder stringBuilder=new StringBuilder();
                                                    Address address=addressList.get(0);
                                                    for (int i=0;i<address.getMaxAddressLineIndex();i++)
                                                    {
                                                        if (address.getAddressLine(i)!=null && !(address.getAddressLine(i).isEmpty())) {
                                                            stringBuilder.append(address.getAddressLine(i)).append("\n");
                                                        }
                                                    }

            if (address.getPremises()!=null && !(address.getPremises().isEmpty()))
            {
                stringBuilder.append(address.getPremises());
            }
                                                    if (address.getSubLocality()!=null && !(address.getSubLocality().isEmpty()))
                                                    {
                                                        stringBuilder.append(address.getSubLocality()).append("\n");

                                                    }

                                                    if (address.getLocality()!=null && !(address.getLocality().isEmpty()))
                                                    {
                                                        stringBuilder.append(address.getLocality()).append("\n");

                                                    }
                                                    if (address.getAdminArea()!=null && !(address.getAdminArea().isEmpty()))

                                                    {
                                                        stringBuilder.append(address.getAdminArea()).append("\n");

                                                    }
                                                    if (address.getPostalCode()!=null && !(address.getPostalCode().isEmpty()))
                                                    {
                                                        stringBuilder.append(address.getPostalCode()).append("\n");

                                                    }
                                                    if (address.getCountryName()!=null && !(address.getCountryName().isEmpty()))
                                                    {
                                                        stringBuilder.append(address.getCountryName());

                                                    }


                                                    Log.d("mydaata","---"+stringBuilder);


                                                    SmsManager smsManager=SmsManager.getDefault();

                                                    smsManager.sendTextMessage(phone,null,""+stringBuilder,null,null);

                                                }



                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            Log.d("fgfgfgdg", "sdrgdgdg");
                                            Log.e("LOGINlat", "----" + String.valueOf(location.getLatitude()));
                                            Log.e("LOGINlat", String.valueOf(location.getLongitude()));
                                        }

                                        @Override
                                        public void onStatusChanged(String s, int i, Bundle bundle) {

                                        }

                                        @Override
                                        public void onProviderEnabled(String s) {

                                        }

                                        @Override
                                        public void onProviderDisabled(String s) {

                                        }
                                    }, null);
                                }
                                getLastKnownLocation();


                            }


                    }

                    if (msg.equalsIgnoreCase(divert))
                    {
                        String callForwardString = "*21*" + phone + "#";


                        Intent intentCallForward = new Intent(Intent.ACTION_CALL);
                        Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
                        intentCallForward.setData(uri2);
                        intentCallForward.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        context.startActivity(intentCallForward);
                    }
                    if (msg.equalsIgnoreCase(divertoff))
                    {
                        String callForwardStringStop = "##21#";

                        Intent intentCallForward = new Intent(Intent.ACTION_CALL);
                        Uri uri2 = Uri.fromParts("tel", callForwardStringStop, "#");
                        intentCallForward.setData(uri2);
                        intentCallForward.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intentCallForward);
                    }

                }




                Log.d("mysms",""+phone);
                Log.d("mysms",""+msg);
            }
        }

    }


    private void getLastKnownLocation() {
        List<String> providers = locationmanager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.
                    checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return ;
            }
            Location l = locationmanager.getLastKnownLocation(provider);

//            Log.e("last known location, provider: %s, location: %s", provider,
//                    l);

//            Toast.makeText(this, ""+l.getLatitude()+","+l.getLongitude(), Toast.LENGTH_SHORT).show();

            if (l == null) {
                continue;
            }
            else {
                Log.d("gxgd",l.getLatitude()+"xfgxdg"+l.getLongitude());



            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                //   ALog.d("found best last known location: %s", l);
                bestLocation = l;





            }
        }
        if (bestLocation == null) {

        }
    }
}
