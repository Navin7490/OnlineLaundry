package navin.laundry.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import navin.laundry.myproject.R;


import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Place_order_Activity extends AppCompatActivity implements LocationListener{
     Button btnorder,btncurrentlocation;
     EditText etname,etphone1,etphone2,etaddress,etdate,ettime;
     DatePickerDialog.OnDateSetListener onDateSetListener;
     String username,mobile1,mobile2,address,date,time;
     TimePickerDialog timePickerDialog;
     String mobile="";
   //  int hour,minute;
   LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order_);
        btnorder=findViewById(R.id.BTNOrder);
        btncurrentlocation=findViewById(R.id.Btn_PCurrentLocation);

        etdate=findViewById(R.id.EtDate);
        etname=findViewById(R.id.EtName);
        etphone1=findViewById(R.id.EtMobileNumber);
        etphone2=findViewById(R.id.EtMobileNumberAlternativ);
        etaddress=findViewById(R.id.EtAddress);
        ettime=findViewById(R.id.Ettime);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Place Order");
         Calendar calendar=Calendar.getInstance();
         final String currentdate= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        final Date timenow=Calendar.getInstance().getTime();

        // get location clicked event
        btncurrentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                checkLocationIsEnabledOrNot();
                grantedPermission();
            }
        });


        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(Place_order_Activity.this,onDateSetListener,year,month,day);
                datePickerDialog.show();
            }
        });
        onDateSetListener =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date=dayOfMonth+"/"+month+"/"+year;
                etdate.setText(date);

            }
        };

        ettime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                final int  hour=calendar.get(Calendar.HOUR_OF_DAY);
                final int  minute=calendar.get(Calendar.MINUTE);
                 timePickerDialog=new TimePickerDialog(Place_order_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time= hourOfDay+":"+minute;
                    ettime.setText(time);

                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        });

        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=etname.getText().toString();
                mobile1=etphone1.getText().toString();
                mobile2=etphone2.getText().toString();
                address=etaddress.getText().toString();
                date=etdate.getText().toString();
                time=ettime.getText().toString();

                //Toast.makeText(Place_order_Activity.this, ""+mobile, Toast.LENGTH_SHORT).show();
                if (username.isEmpty()){
                    etname.requestFocus();
                    etname.setError("Please enter name");
                }
                else if (mobile1.isEmpty()){
                    etphone1.requestFocus();
                    etphone1.setError("Please enter mobile number");

                }
                else if (mobile1.length()<10){
                    etphone1.requestFocus();
                    etphone1.setError("Please enter valid mobile number");
                }
                else if (mobile2.isEmpty()){
                    etphone2.requestFocus();
                    etphone2.setError("Please enter mobile number");

                }
                else if (mobile2.length()<10){
                    etphone2.requestFocus();
                    etphone2.setError("Please enter valid mobile number");
                }

                else if (address.isEmpty()){
                    etaddress.requestFocus();
                    etaddress.setError("Please enter address");
                }
                else if (address.length()<8){
                    etaddress.requestFocus();
                    etaddress.setError("Building/street/society,landmark");
                }
                else if (date.isEmpty()){
                    etdate.requestFocus();
                    etdate.setError("Select pickupdate");
                }
                else if (time.isEmpty()){
                    ettime.requestFocus();
                    ettime.setError("Select pickuptime");
                }
                else {
                    mobile= mobile1+",".concat(mobile2);
                    User_Order_Modal order = new User_Order_Modal(Place_order_Activity.this);
                    order.setOrderdate(String.valueOf(timenow));
                    order.setUsername(username);
                    order.setMobile(mobile1);
                    order.setMobileoptional(mobile);
                    order.setAddress(address);
                    order.setPickupdate(date);
                    order.setPickuptime(time);
                    Intent paymentgo = new Intent(getApplicationContext(), Purchases_Activity.class);
                    startActivity(paymentgo);
                }
            }
        });
    }

    // location method
    private void getLocation() {

        try {

            locationManager= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,5,(LocationListener) this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }

    private void checkLocationIsEnabledOrNot() {

        LocationManager lm= (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled=false;
        boolean networkEnabled=false;

        try {
            gpsEnabled=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            networkEnabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (!gpsEnabled && !networkEnabled){


            new AlertDialog.Builder(Place_order_Activity.this)
                    .setTitle("Enable GPS Service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // this intent redirect us to the location setting , if GPS is disabled this dialog will be show
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel",null)
                    .show();
        }
    }

    private void grantedPermission() {

        if (ContextCompat.checkSelfPermission(Place_order_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED

                && ActivityCompat.checkSelfPermission(Place_order_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(Place_order_Activity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }




    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    // location methods
    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder=new Geocoder(Place_order_Activity.this, Locale.getDefault());

           // List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);


            // etcountry.setText(addresses.get(0).getCountryName());
           // etstate.setText(addresses.get(0).getAdminArea());
           // etdistric.setText(addresses.get(0).getSubAdminArea());
            //etcity.setText(addresses.get(0).getLocality());
            //etaddress1.setText(addresses.get(0).getSubLocality());
            etaddress.setText(addresses.get(0).getAddressLine(0));
           // etpincode.setText(addresses.get(0).getPostalCode());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {


    }

    @Override
    public void onProviderEnabled(String provider) {


    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
