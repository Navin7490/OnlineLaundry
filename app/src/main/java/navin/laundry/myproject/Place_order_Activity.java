package navin.laundry.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import navin.laundry.myproject.R;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class Place_order_Activity extends AppCompatActivity {
     Button btnorder;
     EditText etname,etphone1,etphone2,etaddress,etdate,ettime;
     DatePickerDialog.OnDateSetListener onDateSetListener;
     String username,mobile1,mobile2,address,date,time;
     TimePickerDialog timePickerDialog;
     String mobile="";
   //  int hour,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order_);
        btnorder=findViewById(R.id.BTNOrder);
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
                    order.setMobile(mobile);
                    order.setAddress(address);
                    order.setPickupdate(date);
                    order.setPickuptime(time);
                    Intent paymentgo = new Intent(getApplicationContext(), Select_Payment_Method_Activity.class);
                    startActivity(paymentgo);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
